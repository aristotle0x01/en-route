## 1.环境搭建

### 1.1 install

 https://serverok.in/how-to-install-mysql-5-7-on-centos-7-server

[Install MySQL8 CentOS](https://www.mysqltutorial.org/install-mysql-centos/)

**user & key:** 

​	`root : Abc_!xyz_?123`

**config:**

​	`/etc/my.cnf`

**remote login:**

```
CREATE USER 'root'@'%' IDENTIFIED BY 'Abc_!xyz_?123';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;
```

**便捷登录(免输入-p)：**create a file ~/.my.cnf, permission 600

```
[client]
user=myuser
password=mypassword
```

**error log:**

​	/var/log/mysqld.log

### 1.2 phyadmin

```
docker run --name phpadmin_ddd --net=host -d -e  PMA_HOSTS=10.13.177.203,10.13.172.151,10.185.55.73 -e PMA_PORTS=3306,3306,3306 
registry.xxx.com/bop-devops/phpadmin
```



## 2.命令及工具

`SHOW VARIABLES LIKE 'innodb_flush_log_at_trx_commit'`

`show master status;`

**[py_innodb_page_info](https://github.com/tianyk/py_innodb_page_info.git):**
    `python /var/py_innodb_page_info/py_innodb_page_info.py /var/lib/mysql/ibdata1`



## 3.binlog point-in-time恢复实操

### 3.1 enable binlog

[how to start binlog](https://www.cnblogs.com/grey-wolf/p/10437811.html)

### **3.2 useful commands:**

`show variables like '%bin%';`

`show binlog events in 'mysql-bin.000014';`

`mysqlbinlog -vv  mysql-bin.000014`

### 3.3 场景

模拟误操作后利用mysql全量备份及binlog恢复到A
update t1 set c2 = 12; // A
update t1 set c2 = 13; // B

1) *mysqldump  --flush-logs --delete-master-logs  --all-databases --single-transaction > full-backup.sql*
2) *do A & B, A position=<u>422</u>*
3) *binlog off, drop table t1*
4) *mysql -uroot < full-backup.sql*
5) *mysqlbinlog  --stop-position=<u>422</u> /var/lib/mysql/mysql-bin.000014 | mysql -u root*
6) *binlog on*

### 3.4 ref

[Point-in-Time Recovery Using Event Positions](https://dev.mysql.com/doc/refman/5.7/en/point-in-time-recovery-positions.html)
[How to Restore data from binary log](https://dba.stackexchange.com/questions/29414/how-to-restore-data-from-binary-log)

[Incremental MySQL Server Backup via Binary Log](https://sqlbak.com/blog/incremental-mysql-server-backup-via-binary-log/#:~:text=Incremental%20MySQL%20Server%20Backup%20via%20Binary%20Log%201,to%20cloud%20storage.%20...%204%20Bottom%20Line%20): 全量及增量备份脚本
[Backup MySQL for Fun and Profit!](https://www.continuent.com/resources/blog/backup-mysql-fun-and-profit)：mysqldump, xtrabackup, snapshot三种备份方式



## 4.replication

follow steps in: [How to Configure MySQL Master-Slave Replication on CentOS 7](https://linuxize.com/post/how-to-configure-mysql-master-slave-replication-on-centos-7/)

    m: 10.13.177.203
    s: 10.13.172.151
    
    1) on master
    CREATE USER 'replica'@'10.13.172.151' IDENTIFIED BY 'Abc_!xyz_?123';
    GRANT REPLICATION SLAVE ON *.* TO 'replica'@'10.13.172.151';
    
    2) on slave
    # enable binlog /etc/my.cnf: log-slave-updates
    CHANGE MASTER TO
    MASTER_HOST='10.13.177.203',
    MASTER_USER='replica',
    MASTER_PASSWORD='Abc_!xyz_?123',
    MASTER_LOG_FILE='mysql-bin.000016',
    MASTER_LOG_POS=2185;


    show variables like 'log-slave-updates';



## Q&A

**Q**: read committed & repeatable read 是如何实现的？

A: 取决于read view选取数据版本的时机，每次读最新或者事务起始。innodb mvcc由undo log实现，每个行记录包含了rollback pointer，可以递归回溯变更记录



**Q**: undo log物理上是什么结构？与行记录如何关联？所谓rollback segment是怎么回事？

| <img src="https://user-images.githubusercontent.com/2216435/276671231-d7023575-6b5e-4a22-ad34-98e7bcd8c7a9.png" alt="**InnoDB Architecture**" style="zoom:50%; float: left;" /> | <img src="https://user-images.githubusercontent.com/2216435/276671549-0171afcd-e66d-451e-8da5-221f9623f3c3.png" alt="**InnoDB Architecture**" style="zoom:40%; float: left;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| <img src="https://user-images.githubusercontent.com/2216435/276671902-ff1a151b-5aba-4535-a67d-9ff585908afe.png" alt="**rollback segment**" style="zoom:70%; float: left;" /> | <img src="https://user-images.githubusercontent.com/2216435/276672088-9354f131-2a7b-4133-b14f-125e30b58531.png" alt="undo log chain" style="zoom:35%; float: left;" /> |



**Q**: RC & RR read view对行的版本选取规则？

A:  事务启动时，生成当前所有活跃事务数组**arr**：[trx_min, trx_max]。如果当前行数据trx_id < trx_max且不在**arr**，则对RR可见

<img src="https://user-images.githubusercontent.com/2216435/276672762-28def1df-6a13-4a5f-a8f6-7a85c9d09b2c.png" alt="read view" style="zoom:75%; float: left;" />

```
// MySQL 实战 45 讲：08 | 事务到底是隔离的还是不隔离的？图3
1.如果落在绿色部分，表示这个版本是已提交的事务或者是当前事务自己生成的，这个数据是可见的；
2.如果落在红色部分，表示这个版本是由将来启动的事务生成的，是肯定不可见的；
3.如果落在黄色部分，那就包括两种情况
	a.若 row trx_id 在数组中，表示这个版本是由还没提交的事务生成的，不可见；
	b.若 row trx_id 不在数组中，表示这个版本是已经提交了的事务生成的，可见；

3.b 某事务可能比低水位开始晚，但结束的早；故也可见
```



**Q:** 何时purge undo log? global history list?

A:  global 保存有变更行的必要undo log信息，否则需要全表遍历来purge。暂作此解

<img src="https://user-images.githubusercontent.com/2216435/276673066-139a60e5-4a06-400c-a246-c2cb8971a9f7.png" alt="history list" style="zoom:45%; float: left;" />

**Q**: undo 产生redo?

A:  undo是保证事务acid的一部分，其名有误导，其实也是数据，故需redo保证可靠性



**undo ref**

[The basics of the InnoDB undo logging and history system](https://blog.jcole.us/2014/04/16/the-basics-of-the-innodb-undo-logging-and-history-system/)

[庖丁解InnoDB之Undo LOG](http://catkang.github.io/2021/10/30/mysql-undo.html)

[MySQL 实战 45 讲: 08 | 事务到底是隔离的还是不隔离的？](https://time.geekbang.org/column/article/70562)

[MySQL · 引擎特性 · InnoDB undo log 漫游](http://mysql.taobao.org/monthly/2015/04/01/)

------

**Q:** redo是干什么的？

A:  innodb层面保障事务acid特性的WAL机制

a). [02 | 日志系统：一条SQL更新语句是如何执行的？](https://time.geekbang.org/column/article/68633)里使用了酒馆里账本和小黑板的比喻来最终持久化和redolog，非常形象，但是会让人误以为数据文件持久化是通过checkpoint操作将redolog里的变更写入数据文件来达成的。实际上数据文件变更正常是通过脏页刷盘持久化的。更精确的比喻也许是老板凭借记忆力入账，若有遗忘，则借助小黑板恢复

b).变更记录推进**write pos**后移；脏页刷盘推进**checkpoint**后移

[A graph a day, keeps the doctor away ! – MySQL Checkpoint Age](https://lefred.be/content/a-graph-a-day-keeps-the-doctor-away-mysql-checkpoint-age/)

> We know that when a transaction that modifies data is committed, the change is written in the **Write Ahead Log** (flushed and synced to disk for durability). That write is done to the head of the redo log. This makes the head advance.
>
> The flushing process that writes the dirty pages from the Buffer Pool to the tablespaces moves the **tail** forward the corresponding data changes in the write ahead log.. Then that space can be reused. The tail and the head can only move forward (clockwise on the illustration above).

c). innodb运行中使用fuzzy checkpointing，避免影响性能；选取脏页中***oldest LSN***作为新的checkpoint，更早的都已经刷盘

| <img src="https://user-images.githubusercontent.com/2216435/276674228-415292ca-8474-4d6c-9849-ca0629d2a2b4.png" alt="redo log file" style="zoom:70%; float: left;" /> | <img src="https://user-images.githubusercontent.com/2216435/276674362-712f0e9d-8569-4fe2-a726-7a040c5b7f6d.png" alt="checkpoint" style="zoom:70%; float: left;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ |

d).既然是刷脏页就会有个疑问：未提交的脏页落盘岂非会引起不一致？重启时会利用redolog回滚[does automatic checkpoint flushes dirty pages which are related to un-committed transactions to MDF File in SQL Server](https://dba.stackexchange.com/questions/254217/does-automatic-checkpoint-flushes-dirty-pages-which-are-related-to-un-committed)

**ref:**

[MySQL 日志：undo log、redo log、binlog 有什么用？](https://www.xiaolincoding.com/mysql/log/how_update.html#%E4%B8%BA%E4%BB%80%E4%B9%88%E9%9C%80%E8%A6%81-undo-log)

[MySQL三大日志(binlog、redo log和undo log)详解](https://javaguide.cn/database/mysql/mysql-logs.html)



**Q:** redo/binlog如何保证事务一致性？

A:  本质上由redo/undo来保证事务一致性; binlog用来归档和复制



**Q:** what is checkpoint？

A:  redo log file固定大小，循环使用，且为了缩短故障恢复时长，故要checkpoint

​     a). checkpoint 到底是怎么进行的？

​     b). 如果有一个事务长时间不提交而redolog用尽，此时如何处理？

[How InnoDB performs a checkpoint](https://planet.mysql.com/entry/?id=27130)

[The relationship between Innodb Log checkpointing and dirty Buffer pool pages](https://www.percona.com/blog/the-relationship-between-innodb-log-checkpointing-and-dirty-buffer-pool-pages/)



**Q:** flush dirty pages if changes are uncommitted？

A:  dirty pages will be flushed no matter committed or not. if not and crashed, it can be rollbacked by redo/undo log

[does automatic checkpoint flushes dirty pages which are related to un-committed transactions to MDF File in SQL Server](https://dba.stackexchange.com/questions/254217/does-automatic-checkpoint-flushes-dirty-pages-which-are-related-to-un-committed)



**Q:** with redolog and checkpoint, why does it still need to flush dirty pages？

A:  



**Q:** redo/binlog tx commit order & group commit？

A:  从[Binary Log Group Commit in MySQL 5.6](http://mysqlmusings.blogspot.com/2012/06/binary-log-group-commit-in-mysql-56.html) redo/binlog 并发场景下的两阶段提交的乱序(比如先binlog commit, innodb后commit的情况)本身并不会导致数据一致性问题，而是会导致在线备份数据恢复从节点数据丢失的问题。`prepare_commit_mutex`这个参数的引入有点其它模块有病，innodb吃药的感觉，但都属于mysql框架也可以理解。ref [Fixing MySQL group commit (part 2)](https://knielsen-hq.org/w/fixing-mysql-group-commit-part-2/):

> So, why does InnoDB hold the problematic `prepare_commit_mutex` across the binary logging? That turns out to be a really good question. After extensive research into the issue, it appears that in fact there is no good reason at all for the mutex to be held.
>
> Comments in the InnoDB code, in the bug tracker, and elsewhere, mention that taking the mutex is necessary to ensure that commits happen in the same order in InnoDB and in the binary log. This is certainly true; without taking the mutex we can have transaction A committed in InnoDB before transaction B, but B written to the binary log before transaction A.
>
> But this just raises the next question: why is it necessary to ensure the same commit order in InnoDB and in the binary log? The only reason that I could find stated is that this is needed for InnoDB hot backup and XtraBackup to be able to extract the correct binary log position corresponding to the state of the engine contained in the backup

**乱/顺序示意：**

|                             乱序                             |                             顺序                             |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://user-images.githubusercontent.com/2216435/276676103-9e59f4cd-f282-4963-85a8-d11fc593c2bc.png" alt="out of sequence" style="zoom:60%; float: left;" /> | <img src="https://user-images.githubusercontent.com/2216435/276675453-192f5caa-91ec-4eb7-a621-79fb69bb8812.png" alt="in sequence" style="zoom:60%; float: left;" /> |

**上面乱序造成问题：** 

1）t10时online backup：**B**保存最后提交事务点位(对应t6)，即为t9时完成的事务T3

2）利用**B**在从节点恢复数据，T1在t10时尚未提交，故被回滚

3）从t6时对应点位开始恢复，最终造成T1事务丢失

同理分析顺序则不会



**Q:** group commit？

A:  

| <img src="https://user-images.githubusercontent.com/2216435/276676962-160c4240-693d-4add-be30-bc9327370ca0.png" alt="queue" style="zoom:50%; float: left;" /> | <img src="https://user-images.githubusercontent.com/2216435/276677034-9c70b697-7c9e-4bb4-925e-d15e63fa1f60.png" alt="stage" style="zoom:70%; float: left;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [ref1](https://www.xiaolincoding.com/mysql/log/how_update.html#%E4%B8%A4%E9%98%B6%E6%AE%B5%E6%8F%90%E4%BA%A4%E6%9C%89%E4%BB%80%E4%B9%88%E9%97%AE%E9%A2%98) | [ref2](https://smartkeyerror.oss-cn-shenzhen.aliyuncs.com/Psyduck/MySQL/InnoDB%20Group%20Commit.pdf) |



**Q:** 有哪些关键参数？

A:  innodb_flush_log_at_trx_commit, sync_binlog, innodb_support_xa



**LSN**

> Acronym for “log sequence number”. This arbitrary, ever-increasing value represents a point in time corresponding to operations recorded in the ***\*redo log\****. (This point in time is regardless of ***\*transaction\**** boundaries; it can fall in the middle of one or more transactions.) It is used internally by `InnoDB` during ***\*crash recovery\**** and for managing the ***\*buffer pool\****.
>
> **8** bytes long since MySQL 5.6.3 when the redo log file size limit increased from 4GB to 512GB



------

**Q:** 何为意向锁，有何作用？

A:  意向锁主要是辅助表级和行级锁冲突判断。因为InnoDB支持行级锁，如果没有意向锁，那么判断表级锁和行级锁冲突就需要遍历所有行的行锁

**The intention locking protocol is as follows:**

- Before a transaction can acquire a shared lock on a row in a table, it must first acquire an `IS` lock or stronger on the table.
- Before a transaction can acquire an exclusive lock on a row in a table, it must first acquire an `IX` lock on the table.

ref:

```
// first enable to show lock info
SET GLOBAL innodb_status_output_locks=ON;

show engine innodb status\G;
# output: 
# 	TABLE LOCK table `db1`.`t1` trx id 23359 lock mode IX
# 	RECORD LOCKS space id 141 page no 3 n bits 72 index PRIMARY of table `db1`.`t1` trx id 
# 	23359 lock_mode X locks rec but not gap Record lock, heap no 2 PHYSICAL RECORD: n_fields 
# 	4; compact format; info bits 0

select * from information_schema.INNODB_TRX
```

[MySQL的意向共享锁、意向排它锁和死锁是什么](https://cn.pingcap.com/article/post/7346.html)

[聊一聊 MySQL 中的意向锁](https://www.modb.pro/db/129837)



**Q:** gap lock and next-key lock？

A:  the two are almost the same. 

1. 工作在repeatable read隔离级别下

   > By default, `InnoDB` operates in [`REPEATABLE READ`](https://dev.mysql.com/doc/refman/5.7/en/innodb-transaction-isolation-levels.html#isolevel_repeatable-read) transaction isolation level. In this case, `InnoDB` uses next-key locks for searches and index scans, which prevents phantom rows

2. 所谓gap为何物

   gap锁的关键就是锁住索引树的叶子节点之间的间隙，不让新的记录插入到间隙之中

   <img src="https://user-images.githubusercontent.com/2216435/276677598-71f325d2-e059-4ba2-b1f5-fe7170a99f9a.png" alt="gap view" style="zoom:60%; float: left;" />

3. 加锁规则与范围

   由next-key根据索引，范围，条件值是否存在等判断，next-key可能退化为gap or record lock；

   锁的数量与数据量和锁定列取值直接相关；

   不走索引列全表加next-key lock



ref:

- [深入了解mysql--gap locks,Next-Key Locks](https://www.cnblogs.com/chongaizhen/p/11168442.html)

  ```
  create table gap_tbz
  (
    id   int default 0 not null
      primary key,
    name varchar(11) not null
  );
  
  INSERT INTO test.gap_tbz (id, name) VALUES (1, 'a');
  INSERT INTO test.gap_tbz (id, name) VALUES (5, 'h');
  INSERT INTO test.gap_tbz (id, name) VALUES (8, 'm');
  INSERT INTO test.gap_tbz (id, name) VALUES (11, 'ds');
  ```

  session1:  `select * from gap_tbz where id < 11 for update;`

  session2:  `insert into gap_tbz values(11,'cc');`

  ​                  mysql5.7: 报错 lock等待超时

  ​                  Mysql8.0: 主键重复

  可见session1在mysql5.7下加next-key lock，在8.0下加gap-lock

- [MySQL 是怎么加锁的？](https://xiaolincoding.com/mysql/lock/how_to_lock.html#%E6%B2%A1%E6%9C%89%E5%8A%A0%E7%B4%A2%E5%BC%95%E7%9A%84%E6%9F%A5%E8%AF%A2)

- [MySQL Data Locks: Mapping 8.0 to 5.7](https://hackmysql.com/post/mysql-data-locks-mapping-80-to-57/)

  performance_schema.data_locks only exists in 8.0, then how to view locking in 5.7

  <img src="https://user-images.githubusercontent.com/2216435/276677692-e24011a8-56de-4b59-8bea-d338e6e18374.png" alt="8.0->5.7 lock mapping" style="zoom:50%; float: left;" />

- 查看锁情况

  ```
  SET innodb_lock_wait_timeout = 180;
  select * from information_schema.INNODB_LOCKS\G;
  select * from performance_schema.metadata_locks\G;
  select * from information_schema.innodb_trx\G;
  show engine innodb status\G;
  
  # 8.0 only
  select * from performance_schema.data_locks\G; 
  ```

------

innodb文件结构

**Q:** 何为index organized table？

A:  innodb以B+tree形式组织数据表/索引文件；**数据页之间逻辑连续，物理上不连续**

| <img src="https://user-images.githubusercontent.com/2216435/276677939-b05d3cb7-140a-4d4f-8014-c0da7efa7455.png" alt="page structure" style="zoom:50%; float: left;" /> | <img src="https://user-images.githubusercontent.com/2216435/276677857-3df873e3-afc3-4251-b1a0-c2db7ffae168.png" alt="page and row" style="zoom:50%; float: left;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ |

| <img src="https://user-images.githubusercontent.com/2216435/276677797-1a8a8e8c-8ba4-4555-8919-038d7bf70782.png" alt="page index tree" style="zoom:70%; float: left;" /> |
| ------------------------------------------------------------ |

[从数据页的角度看 B+ 树](https://www.xiaolincoding.com/mysql/index/page.html#innodb-%E6%98%AF%E5%A6%82%E4%BD%95%E5%AD%98%E5%82%A8%E6%95%B0%E6%8D%AE%E7%9A%84)



## ref

[transaction processing concept and techniques](https://pdfrock.com/compress-pdf-free.html)： **jim gray**