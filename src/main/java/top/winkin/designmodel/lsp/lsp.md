里氏替换原则:
    1、子类必须完全实现父类的方法 如果子类不能完全实现父类的方法，
       或父类的默写方法在子类中发生了畸变，建议采用依赖，聚集，组合方法替代继承。
       详见：{@link top.winkin.designmodel/lsp/Client1.java}
    2、子类只能重载父类实现好的方法或重写父类抽象方法 保证父类对象出现的地方，子类对象可以替换
       详见：top.winkin.designmodel/lsp/Client2.java