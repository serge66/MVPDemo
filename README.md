

Android MVP架构从入门到精通-真枪实弹

![](https://user-gold-cdn.xitu.io/2018/11/23/1673ef6171b26726?w=904&h=1270&f=png&s=136089)

#### 一. 前言

你是否遇到过Activity/Fragment中成百上千行代码,完全无法维护,看着头疼?

你是否遇到过因后台接口还未写而你不能先写代码逻辑的情况?

你是否遇到过用MVC架构写的项目进行单元测试时的深深无奈?

如果你现在还是用MVC架构模式在写项目,请先转到MVP模式!

#### 二. MVC架构

MVC架构模式最初生根于服务器端的Web开发，后来渐渐能够胜任客户端Web开发，再后来因Android项目由XML和Activity/Fragment组成,慢慢的Android开发者开始使用类似MVC的架构模式开发应用.

![mvc架构模式](https://img-blog.csdnimg.cn/20181114163503719.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3ZpdGFtaW8=,size_39,color_FFFFFF,t_70)

M层:模型层(model),主要是实体类,数据库,网络等存在的层面,model将新的数据发送到view层,用户得到数据响应.

V层:视图层(view),一般指XML为代表的视图界面.显示来源于model层的数据.用户的点击操作等事件从view层传递到controller层.

C层:控制层(controller),一般以Activity/Fragment为代表.C层主要是连接V层和M层的,C层收到V层发送过来的事件请求,从M层获取数据,展示给V层.

从上图可以看出M层和V层有连接关系,而Activity有时候既充当了控制层又充当了视图层,导致项目维护比较麻烦.

##### 1. MVC架构优缺点

###### A. 缺点

1. M层和V层有连接关系,没有解耦,导致维护困难.

2. Activity/Fragment中的代码过多,难以维护.

Activity中有很多关于视图UI的显示代码，因此View视图和Activity控制器并不是完全分离的，当Activity类业务过多的时候，会变得难以管理和维护.尤其是当UI的状态数据，跟持久化的数据混杂在一起，变得极为混乱.

###### B. 优点

1. 控制层和View层都在Activity中进行操作，数据操作方便.

2. 模块职责划分明确.主要划分层M,V,C三个模块.

#### 三. MVP架构

![MVP](https://img-blog.csdnimg.cn/2018112115535266.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3ZpdGFtaW8=,size_29,color_FFFFFF,t_70)

MVP,即是Model,View,Presenter架构模式.看起来类似MVC,其实不然.从上图能看到Model层和View层没有相连接,完全解耦.

用户触碰界面触发事件,View层把事件通知Presenter层,Presenter层通知Model层处理这个事件,Model层处理后把结果发送到Presenter层,Presenter层再通知View层,最后View层做出改变.这是一整套流程.

M层:模型层(Model),此层和MVC中的M层作用类似.

V层:视图层(View),在MVC中V层只包含XML文件,而MVP中V层包含XML,Activity和Fragment三者.理论上V层不涉及任何逻辑,只负责界面的改变,尽量把逻辑处理放到M层.

P层:通知层(Presenter),P层的主要作用就是连接V层和M层,起到一个通知传递数据的作用.

##### 1. MVP架构优缺点

###### A. 缺点

1. MVP中接口过多.

2. 每一个功能,相比于MVC要多写好几个文件.

3. 如果某一个界面中需要请求多个服务器接口,这个界面文件中会实现很多的回调接口,导致代码繁杂.

4. 如果更改了数据源和请求中参数,会导致更多的代码修改.

5. 额外的代码复杂度及学习成本.

###### B. 优点

1. 模块职责划分明显,层次清晰,接口功能清晰.

2. Model层和View层分离,解耦.修改View而不影响Model.

3. 功能复用度高,方便.一个Presenter可以复用于多个View,而不用更改Presenter的逻辑.

4. 有利于测试驱动开发,以前的Android开发是难以进行单元测试.

5. 如果后台接口还未写好,但已知返回数据类型的情况下,完全可以写出此接口完整的功能.

#### 四. MVP架构实战(真枪实弹)

##### 1. MVP三层代码简单书写

接下来笔者从简到繁,一点一点的堆砌MVP的整个架构.先看一下XML布局,布局中一个Button按钮和一个TextView控件,用户点击按钮后,Presenter层通知Model层请求处理网络数据,处理后Model层把结果数据发送给Presenter层,Presenter层再通知View层,然后View层改变TextView显示的内容.


![MVP](https://img-blog.csdnimg.cn/20181123112130974.gif)

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_gravity="center"
    android:gravity="center"
    android:orientation="vertical"
    tools:context=".view.SingleInterfaceActivity">

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="点击" />

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="100px"
        android:text="请点击上方按钮获取数据" />
</LinearLayout>
```
接下来是Activity代码,里面就是获取Button和TextView控件,然后对Button做监听,先简单的这样写,一会慢慢的增加代码.

```
public class SingleInterfaceActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_interface);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
```
下面是Model层代码.本次网络请求用的是wanandroid网站的开放api,其中的文章首页列表接口.SingleInterfaceModel文件里面有一个方法getData,第一个参数curPage意思是获取第几页的数据,第二个参数callback是Model层通知Presenter层的回调.

```
public class SingleInterfaceModel {

    public void getData(int curPage, final Callback callback) {
        NetUtils.getRetrofit()
                .create(Api.class)
                .getData(curPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArticleListBean>() {
                    @Override
                    public void onCompleted() {
                        LP.w("completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail("出现错误");
                    }

                    @Override
                    public void onNext(ArticleListBean bean) {
                        if (null == bean) {
                            callback.onFail("出现错误");
                        } else if (bean.errorCode != 0) {
                            callback.onFail(bean.errorMsg);
                        } else {
                            callback.onSuccess(bean);
                        }
                    }
                });
    }
}
```
Callback文件内容如下.里面一个成功一个失败的回调接口,参数全是泛型,为啥使用泛型笔者就不用说了吧.

```
public interface Callback<K, V> {
    void onSuccess(K data);

    void onFail(V data);
}
```
再接下来是Presenter层的代码.SingleInterfacePresenter类构造函数中直接new了一个Model层对象,用于Presenter层对Model层的调用.然后SingleInterfacePresenter类的方法getData用于与Model的互相连接.

```
public class SingleInterfacePresenter {
    private final SingleInterfaceModel singleInterfaceModel;

    public SingleInterfacePresenter() {
        this.singleInterfaceModel = new SingleInterfaceModel();
    }

    public void getData(int curPage) {
        singleInterfaceModel.getData(curPage, new Callback<ArticleListBean, String>() {
            @Override
            public void onSuccess(ArticleListBean loginResultBean) {
                //如果Model层请求数据成功,则此处应执行通知View层的代码

            }

            @Override
            public void onFail(String errorMsg) {
                //如果Model层请求数据失败,则此处应执行通知View层的代码

            }
        });
    }
}
```
至此,MVP三层简单的部分代码算是完成.那么怎样进行整个流程的相互调用呢.我们把刚开始的SingleInterfaceActivity代码改一下,让SingleInterfaceActivity持有Presenter层的对象,这样View层就可以调用Presenter层了.修改后代码如下.

```
public class SingleInterfaceActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private SingleInterfacePresenter singleInterfacePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_interface);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        singleInterfacePresenter = new SingleInterfacePresenter();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleInterfacePresenter.getData(0);
            }
        });

    }
}
```

从以上所有代码可以看出,当用户点击按钮后,View层按钮的监听事件执行调用了Presenter层对象的getData方法,此时,Presenter层对象的getData方法调用了Model层对象的getData方法,Model层对象的getData方法中执行了网络请求和逻辑处理,把成功或失败的结果通过Callback接口回调给了Presenter层,然后Presenter层再通知View层改变界面.但此时SingleInterfacePresenter类中收到Model层的结果后无法通知View层,因为SingleInterfacePresenter未持有View层的对象.如下代码的注释中有说明.(如果此时点击按钮,下方代码LP.w()处会打印出网络请求成功的log)

```
public class SingleInterfacePresenter {
    private final SingleInterfaceModel singleInterfaceModel;

    public SingleInterfacePresenter() {
        this.singleInterfaceModel = new SingleInterfaceModel();
    }

    public void getData(int curPage) {
        singleInterfaceModel.getData(curPage, new Callback<ArticleListBean, String>() {
            @Override
            public void onSuccess(ArticleListBean loginResultBean) {
                //如果Model层请求数据成功,则此处应执行通知View层的代码
                //LP.w()是一个简单的log打印
                LP.w(loginResultBean.toString());
            }

            @Override
            public void onFail(String errorMsg) {
                //如果Model层请求数据失败,则此处应执行通知View层的代码

            }
        });
    }
}
```
代码写到这里,笔者先把这些代码提交到github([https://github.com/serge66/MVPDemo](https://github.com/serge66/MVPDemo)),github上会有一次提交记录,如果想看此时的代码,可以根据提交记录"*第一次修改*"克隆此时的代码.


##### 2. P层V层沟通桥梁

现在P层未持有V层对象,不能通知V层改变界面,那么就继续演变MVP架构.
在MVP架构中,我们要为每个Activity/Fragment写一个接口,这个接口需要让Presenter层持有,P层通过这个接口去通知V层更改界面.接口中包含了成功和失败的回调,这个接口Activity/Fragment要去实现,最终P层才能通知V层.

```
public interface SingleInterfaceIView {
    void showArticleSuccess(ArticleListBean bean);

    void showArticleFail(String errorMsg);
}
```

一个完整的项目以后肯定会有许多功能界面,那么我们应该抽出一个IView公共接口,让所有的Activity/Fragment都间接实现它.IVew公共接口是用于给View层的接口继承的,注意,不是View本身继承.因为它定义的是接口的规范, 而其他接口才是定义的类的规范(这句话请仔细理解).

```
/**
 * @Description: 公共接口 是用于给View的接口继承的，注意，不是View本身继承。
 * 					因为它定义的是接口的规范， 而其他接口才是定义的类的规范
 * @Author: lishengjiejob@163.com
 * @Time: 2018/11/22 17:26
 */
public interface IView {
}
```
这个接口中可以写一些所有Activigy/Fragment共用的方法,我们把SingleInterfaceIView继承IView接口.

```
public interface SingleInterfaceIView extends IView {
    void showArticleSuccess(ArticleListBean bean);

    void showArticleFail(String errorMsg);
}
```
同理Model层和Presenter层也是如此.

```
public interface IModel {
}
```

```
public interface IPresenter {
}
```
现在项目中Model层是一个SingleInterfaceModel类,这个类对象被P层持有,对于面向对象设计来讲,利用接口达到解耦目的已经人尽皆知,那我们就要对SingleInterfaceModel类再写一个可继承的接口.代码如下.

```
public interface ISingleInterfaceModel extends IModel {
    void getData(int curPage, final Callback callback);
}
```
如此,SingleInterfaceModel类的修改如下.

```
public class SingleInterfaceModel implements ISingleInterfaceModel {

    @Override
    public void getData(int curPage, final Callback callback) {
        NetUtils.getRetrofit()
                .create(Api.class)
                .getData(curPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArticleListBean>() {
                    @Override
                    public void onCompleted() {
                        LP.w("completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFail("出现错误");
                    }

                    @Override
                    public void onNext(ArticleListBean bean) {
                        if (null == bean) {
                            callback.onFail("出现错误");
                        } else if (bean.errorCode != 0) {
                            callback.onFail(bean.errorMsg);
                        } else {
                            callback.onSuccess(bean);
                        }
                    }
                });
    }
}
```

同理,View层持有P层对象,我们也需要对P层进行改造.但是下面的代码却没有像ISingleInterfaceModel接口继承IModel一样继承IPresenter,这点需要注意,笔者把IPresenter的继承放在了其他处,后面会讲解.

```
public interface ISingleInterfacePresenter {
    void getData(int curPage);
}

```

然后SingleInterfacePresenter类的修改如下:

```
public class SingleInterfacePresenter implements ISingleInterfacePresenter {
    private final ISingleInterfaceModel singleInterfaceModel;

    public SingleInterfacePresenter() {
        this.singleInterfaceModel = new SingleInterfaceModel();
    }

    @Override
    public void getData(int curPage) {
        singleInterfaceModel.getData(curPage, new Callback<ArticleListBean, String>() {
            @Override
            public void onSuccess(ArticleListBean loginResultBean) {
                //如果Model层请求数据成功,则此处应执行通知View层的代码
                //LP.w()是一个简单的log打印
                LP.w(loginResultBean.toString());
            }

            @Override
            public void onFail(String errorMsg) {
                //如果Model层请求数据失败,则此处应执行通知View层的代码
                LP.w(errorMsg);
            }
        });
    }
}
```
##### 3. 生命周期适配
至此,MVP三层每层的接口都写好了.但是P层连接V层的桥梁还没有搭建好,这个慢慢来,一个好的高楼大厦都是一步一步建造的.上面IPresenter接口我们没有让其他类继承,接下来就讲下这个.P层和V层相连接,V层的生命周期也要适配到P层,P层的每个功能都要适配生命周期,这里可以把生命周期的适配放在IPresenter接口中.P层持有V层对象,这里把它放到泛型中.代码如下.

```
public interface IPresenter<T extends IView> {

    /**
     * 依附生命view
     *
     * @param view
     */
    void attachView(T view);

    /**
     * 分离View
     */
    void detachView();

    /**
     * 判断View是否已经销毁
     *
     * @return
     */
    boolean isViewAttached();

}
```

这个IPresenter接口需要所有的P层实现类继承,对于生命周期这部分功能都是通用的,那么就可以抽出来一个抽象基类BasePresenter,去实现IPresenter的接口.

```
public abstract class BasePresenter<T extends IView> implements IPresenter<T> {
    protected T mView;

    @Override
    public void attachView(T view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public boolean isViewAttached() {
        return mView != null;
    }
}

```

此时,SingleInterfacePresenter类的代码修改如下.泛型中的SingleInterfaceIView可以理解成对应的Activity,P层此时完成了对V层的通信.

```
public class SingleInterfacePresenter extends BasePresenter<SingleInterfaceIView> implements ISingleInterfacePresenter {
    private final ISingleInterfaceModel singleInterfaceModel;

    public SingleInterfacePresenter() {
        this.singleInterfaceModel = new SingleInterfaceModel();
    }

    @Override
    public void getData(int curPage) {
        singleInterfaceModel.getData(curPage, new Callback<ArticleListBean, String>() {
            @Override
            public void onSuccess(ArticleListBean loginResultBean) {
                //如果Model层请求数据成功,则此处应执行通知View层的代码
                //LP.w()是一个简单的log打印
                LP.w(loginResultBean.toString());
                if (isViewAttached()) {
                    mView.showArticleSuccess(loginResultBean);
                }
            }

            @Override
            public void onFail(String errorMsg) {
                //如果Model层请求数据失败,则此处应执行通知View层的代码
                LP.w(errorMsg);
                if (isViewAttached()) {
                    mView.showArticleFail(errorMsg);
                }
            }
        });
    }
}
```



此时,P层和V层的连接桥梁已经搭建,但还未搭建完成,我们需要写个BaseMVPActvity让所有的Activity继承,统一处理Activity相同逻辑.在BaseMVPActvity中使用IPresenter的泛型,因为每个Activity中需要持有P层对象,这里把P层对象抽出来也放在BaseMVPActvity中.同时BaseMVPActvity中也需要继承IView,用于P层对V层的生命周期中.代码如下.

```
public abstract class BaseMVPActivity<T extends IPresenter> extends AppCompatActivity implements IView {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        init();
    }

    protected void initPresenter() {
        mPresenter = createPresenter();
        //绑定生命周期
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    /**
     * 创建一个Presenter
     *
     * @return
     */
    protected abstract T createPresenter();

    protected abstract void init();

}
```

接下来让SingleInterfaceActivity实现这个BaseMVPActivity.

```
public class SingleInterfaceActivity extends BaseMVPActivity<SingleInterfacePresenter> implements SingleInterfaceIView {

    private Button button;
    private TextView textView;

    @Override
    protected void init() {
        setContentView(R.layout.activity_single_interface);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getData(0);
            }
        });
    }

    @Override
    protected SingleInterfacePresenter createPresenter() {
        return new SingleInterfacePresenter();
    }


    @Override
    public void showArticleSuccess(ArticleListBean bean) {
        textView.setText(bean.data.datas.get(0).title);
    }

    @Override
    public void showArticleFail(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }
}

```

到此,MVP架构的整个简易流程完成.

代码写到这里,笔者先把这些代码提交到github([https://github.com/serge66/MVPDemo](https://github.com/serge66/MVPDemo)),github上会有一次提交记录,如果想看此时的代码,可以根据提交记录"*第二次修改*"克隆此时的代码.


##### 4. 优化MVP架构


![MVP目录](https://img-blog.csdnimg.cn/20181122195903982.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3ZpdGFtaW8=,size_16,color_FFFFFF,t_70)

上面是MVP的目录,从目录中我们可以看到一个功能点(网络请求)MVP三层各有两个文件需要写,相对于MVC来说写起来确实麻烦,这也是一些人不愿意写MVP,宁愿用MVC的原因.

这里我们可以对此优化一下.MVP架构中有个Contract的概念,Contract有统一管理接口的作用,目的是为了统一管理一个页面的View和Presenter接口,用Contract可以减少部分文件的创建,比如P层和V层的接口文件.

那我们就把P层的ISingleInterfacePresenter接口和V层的SingleInterfaceIView接口文件删除掉,放入SingleInterfaceContract文件中.代码如下.


```
public interface SingleInterfaceContract {


    interface View extends IView {
        void showArticleSuccess(ArticleListBean bean);

        void showArticleFail(String errorMsg);
    }

    interface Presenter {
        void getData(int curPage);
    }


}
```
此时,SingleInterfacePresenter和SingleInterfaceActivity的代码修改如下.

```
public class SingleInterfacePresenter extends BasePresenter<SingleInterfaceContract.View>
        implements SingleInterfaceContract.Presenter {

    private final ISingleInterfaceModel singleInterfaceModel;

    public SingleInterfacePresenter() {
        this.singleInterfaceModel = new SingleInterfaceModel();
    }

    @Override
    public void getData(int curPage) {
        singleInterfaceModel.getData(curPage, new Callback<ArticleListBean, String>() {
            @Override
            public void onSuccess(ArticleListBean loginResultBean) {
                //如果Model层请求数据成功,则此处应执行通知View层的代码
                //LP.w()是一个简单的log打印
                LP.w(loginResultBean.toString());
                if (isViewAttached()) {
                    mView.showArticleSuccess(loginResultBean);
                }
            }

            @Override
            public void onFail(String errorMsg) {
                //如果Model层请求数据失败,则此处应执行通知View层的代码
                LP.w(errorMsg);
                if (isViewAttached()) {
                    mView.showArticleFail(errorMsg);
                }
            }
        });
    }
}
```

```
public class SingleInterfaceActivity extends BaseMVPActivity<SingleInterfacePresenter>
        implements SingleInterfaceContract.View {

    private Button button;
    private TextView textView;

    @Override
    protected void init() {
        setContentView(R.layout.activity_single_interface);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getData(0);
            }
        });
    }

    @Override
    protected SingleInterfacePresenter createPresenter() {
        return new SingleInterfacePresenter();
    }


    @Override
    public void showArticleSuccess(ArticleListBean bean) {
        textView.setText(bean.data.datas.get(0).title);
    }

    @Override
    public void showArticleFail(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }
}

```
代码写到这里,笔者先把这些代码提交到github([https://github.com/serge66/MVPDemo](https://github.com/serge66/MVPDemo)),github上会有一次提交记录,如果想看此时的代码,可以根据提交记录"*第三次修改*"克隆此时的代码.

##### 5. 单页面多网络请求(P层复用)

上面的MVP封装只适用于单页面一个网络请求的情况,当一个界面有两个网络请求时,此封装已不适合.为此,我们再次新建一个MultipleInterfaceActivity来进行说明.XML中布局是两个按钮两个Textview,点击则可以进行网络请求.

![MVP](https://img-blog.csdnimg.cn/20181123112245831.gif)

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_gravity="center"
    android:gravity="center"
    android:orientation="vertical"
    tools:context=".view.MultipleInterfaceActivity">

    <Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="点击" />

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="50px"
        android:text="请点击上方按钮获取数据" />

    <Button
        android:id="@+id/btn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="100px"
        android:text="点击" />

    <TextView
        android:id="@+id/tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="50px"
        android:text="请点击上方按钮获取数据" />
</LinearLayout>
```

MultipleInterfaceActivity类代码暂时如下.

```
public class MultipleInterfaceActivity extends BaseMVPActivity {

    private Button button;
    private TextView textView;
    private Button btn;
    private TextView tv;


    @Override
    protected void init() {
        setContentView(R.layout.activity_multiple_interface);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btn = findViewById(R.id.btn);
        tv = findViewById(R.id.tv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

}
```

此时我们可以想下,当一个页面中有多个网络请求时,Activity所继承的BaseMVPActivity的泛型中要写多个参数,那有没有上面代码的框架不变的情况下实现这个需求呢?答案必须有的.我们可以把多个网络请求的功能当做一个网络请求来看待,封装成一个MultiplePresenter,其继承至BasePresenter实现生命周期的适配.此MultiplePresenter类的作用就是容纳多个Presenter,连接同一个View.代码如下.

```
public class MultiplePresenter<T extends IView> extends BasePresenter<T> {
    private T mView;

    private List<IPresenter> presenters = new ArrayList<>();

    @SafeVarargs
    public final <K extends IPresenter<T>> void addPresenter(K... addPresenter) {
        for (K ap : addPresenter) {
            ap.attachView(mView);
            presenters.add(ap);
        }
    }

    public MultiplePresenter(T mView) {
        this.mView = mView;
    }

    @Override
    public void detachView() {
        for (IPresenter presenter : presenters) {
            presenter.detachView();
        }
    }

}
```
因MultiplePresenter类中需要有多个网络请求,现在举例说明时,暂时用两个网络请求接口.MultipleInterfaceActivity类中代码改造如下.


```
public class MultipleInterfaceActivity extends BaseMVPActivity<MultiplePresenter>
        implements SingleInterfaceContract.View, MultipleInterfaceContract.View {

    private Button button;
    private TextView textView;
    private Button btn;
    private TextView tv;
    private SingleInterfacePresenter singleInterfacePresenter;
    private MultipleInterfacePresenter multipleInterfacePresenter;


    @Override
    protected void init() {
        setContentView(R.layout.activity_multiple_interface);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleInterfacePresenter.getData(0);
            }
        });


        btn = findViewById(R.id.btn);
        tv = findViewById(R.id.tv);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleInterfacePresenter.getBanner();
            }
        });
    }

    @Override
    protected MultiplePresenter createPresenter() {
        MultiplePresenter multiplePresenter = new MultiplePresenter(this);

        singleInterfacePresenter = new SingleInterfacePresenter();
        multipleInterfacePresenter = new MultipleInterfacePresenter();

        multiplePresenter.addPresenter(singleInterfacePresenter);
        multiplePresenter.addPresenter(multipleInterfacePresenter);
        return multiplePresenter;
    }

    @Override
    public void showArticleSuccess(ArticleListBean bean) {
        textView.setText(bean.data.datas.get(0).title);
    }

    @Override
    public void showArticleFail(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMultipleSuccess(BannerBean bean) {
        tv.setText(bean.data.get(0).title);
    }

    @Override
    public void showMultipleFail(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
    }
}

```
写到这里,MVP框架基本算是完成.如果想再次优化,其实还是有可优化的地方,比如当View销毁时,现在只是让P层中的View对象置为null,并没有继续对M层通知.如果View销毁时,M层还在请求网络中呢,可以为此再加入一个取消网络请求的通用功能.这里只是举一个例子,每个人对MVP的理解不一样,而MVP架构也并不是一成不变,适合自己项目的才是最好的.

##### 6. 完整项目地址

完整项目已提交到github([https://github.com/serge66/MVPDemo](https://github.com/serge66/MVPDemo)),若需要敬请查看.



#### 五. 参考资料

[一步步带你精通MVP](https://mp.weixin.qq.com/s/DuNbl3V4gZY-ZCETbhZGug)
[从0到1搭建MVP框架](https://mp.weixin.qq.com/s/QFpHhC-5JkAb4IlMP0nKug)
[Presenter层如何高度的复用](https://juejin.im/post/599ce8016fb9a0247e4255f4)

#### 六. 后续

<<MVVM架构从入门到精通-真枪实弹>> 敬请期待~~~

[原创文章，来自于Vitamio(http://blog.csdn.net/vitamio)，转载请注明出处。](http://blog.csdn.net/vitamio)

文章同步发布于:

CSDN https://blog.csdn.net/vitamio/article/details/84069427

掘金 https://juejin.im/post/5bf787d5e51d450c487d06dd

简书 https://www.jianshu.com/p/76c098652dba
