# ikan

本项目为学习kotlin的产物，存在些许不足之处，欢迎提issue。

项目结构应该算清晰简洁，整个工程分为7个子项目

### toolkit

包含一些android常用的工具类，非必须

### support

主工程引用的第三方类库，需要扩展的类库，非必须

### net

网络相关，网络请求的创建以及拦截器等，必须

### mvp

封装工程上层依赖，采取**mvp**模式，之所以委托**view**和**presenter**的依赖建立过程，目的其一：保持**Activity**简洁 ；其二：**presenter**创建以及销毁过程的不会引起**Activity**的过度修改，必须

### eventbus

基于**rxjava**实现的事件总线，提供了两种注册过程，使用方式请查看主工程，非必须

### design

顾名思义，本着职责分明的设计初衷将UI相关的内容单独拆分一个**module**，非必须

### app

主工程模块，多解释一些吧  

呃 

呃

呃......

解耦。恩  ，就这样。

不闹了，粗略解释一下

主工程分为presentation、domain、data 模块, presentation -> domain -> data

presentation采用mvp模式将视图层独立出来，对外提供view接口，presenter持有并以此更新view状态
domain -> data 你可能觉得层次嵌套有些深，但是我可以告诉你不多，已经在解耦和复杂度做了平衡选择，这里repository模式，名词过于生硬，说白了就是domain定义一个获取数据的接口，data层负责去实现就行，至于如何获取的数据是对domain、presentation层透明的。data层就是数据存储相关的了，因为数据不只是从远程服务端获取还有本地存储的，为了做到尽量灵活，我们为repository添加datastore接口，实现分别为cloud和local，这样repository就可以随时切换数据来源。cloud模块没有逃离世俗，采用okhttp+retrofit+rxjava，还为cloud添加token机制（自动刷新token并恢复原有请求调用），请求拦截等，这些功能算是非业务功能，应该多数app都应该有的，而且解决方案不一定合适，所以这里就给出了一种实现。

tip： 如果有什么疑问或者有什么好的提议，可以添加qq群141741586进行探讨








