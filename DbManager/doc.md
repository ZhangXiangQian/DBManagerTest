# DBLibrary使用

## Application初始化

```java
  public class BaseApplication extends Application {

      @Override
      public void onCreate() {
          super.onCreate();
          //初始化
          Controller.Ext.init(this);
          //Debug调试 false则屏蔽所有打印信息
          Controller.Ext.setDebug(true);
      }
  }
```

## 类注解

  在需要存储至数据库的类添加注解，如下：

```java
    //类实体对应的数据库表名
  @Table(name = "GeneralMessage")
  public class GeneralMessage {

      public int getId() {
          return id;
      }

      public void setId(int id) {
          this.id = id;
      }
      // name 指明数据库对应的字段 isId  是否是id
      @Column(name = "id",isId = true)
      private int id;
      @Column(name = "roomId")
      private int roomId;
      @Column(name = "fromName")
      private String fromName;
      @Column(name = "toName")
      private String toName;
      /** 1 未读 ； 2 已读*/
      @Column(name = "isRead")
      private int isRead;
      @Column(name = "roomName")
      private String roomName;
      @Column(name = "content")

      public int getIsMe() {
          return isMe;
      }

      public void setIsMe(int isMe) {
          this.isMe = isMe;
      }

      /**
       * 1 ：是我 right
       * 2：其他人 left
       */
      @Column(name = "isMe")
      private int isMe;

      public int getRoomId() {
          return roomId;
      }

      public void setRoomId(int roomId) {
          this.roomId = roomId;
      }

      public String getFromName() {
          return fromName;
      }

      public void setFromName(String fromName) {
          this.fromName = fromName;
      }

      public int getIsRead() {
          return isRead;
      }

      public void setIsRead(int isRead) {
          this.isRead = isRead;
      }

      public String getToName() {
          return toName;
      }

      public void setToName(String toName) {
          this.toName = toName;
      }

      public String getRoomName() {
          return roomName;
      }

      public void setRoomName(String roomName) {
          this.roomName = roomName;
      }

  }
```

## 数据库操作
 1、在操作数据库之前先初始化 DbManager.DaoConfig；

 ```java

     DbManager.DaoConfig config = new DbManager.DaoConfig()
                   // 数据库名称
                   .setDbName(GeneralMsg.DB_NAME + "_" + BaseApplication.userId)
                   //数据库版本
                   .setDbVersion(1)
                   //数据库更新
                   .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                       @Override
                       public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                       }
                   });

 ```

 1、添加数据

```java

       /**
         * 保存数据
         *这里放的是List,如果仅存放单个数据 将list改为对应的实体即可
         * @param list
         */
        public void save(List<T> list) {
            DbManager db = DbManagerImpl.getInstance(config);
            try {
                if (list != null && list.size() != 0) {
                    db.save(list);
                } else {
                    LogUtil.w("insert data is null");
                }
            } catch (DbException e) {
                e.printStackTrace();
                LogUtil.w(e.getMessage());
            }
        }

```
 2、查询数据

  查询指定数据：
```java

      /**
         * 特定条件查询
         * @param entityType 数据查询的表
         * @param key   字段
         * @param value  对应的值
         */
        public List<T> query(Class<T> entityType,String key,String value){
            DbManager db = DbManagerImpl.getInstance(config);
            try {
                if(!Tools.isNull(key,value)){
                  // “=” 也可 改为“>”，“<” 分别对应 等于、大于、小于对应的值
                  return db.selector(entityType).where(key,"=",value).findAll();
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
            return null;
        }

```

  查询表

```java
   /**
     * 查询数据
     *
     * @param entityType  查询表数据
     */
    public List<T> query(Class<T> entityType) {
        DbManager db = DbManagerImpl.getInstance(config);
        try {
            return db.findAll(entityType);
        } catch (DbException e) {
            e.printStackTrace();
            LogUtil.w(entityType + "...查询失败");
        }
        return null;
    }

```
3、更新数据

```java
      /**
       * 保存数据 这里传入的是List,如果更新单个数据 将list改为对应的数据即可
       *
       * @param list
       */
      public void save(List<T> list) {
          DbManager db = DbManagerImpl.getInstance(config);
          try {
              if (list != null && list.size() != 0) {
                 // 如果不知道是否更新还是插入 就调用db.saveOrUpdate(list);
                  db.update(list);
              } else {
                  LogUtil.w("insert data is null");
              }
          } catch (DbException e) {
              e.printStackTrace();
              LogUtil.w(e.getMessage());
          }
      }
```