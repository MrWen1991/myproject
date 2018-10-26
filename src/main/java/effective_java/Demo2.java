package effective_java;

/**
 * @Description: 多个构造参数时 构建器构造javaBean 防止破坏javaBean的完整性 同时可校验参数
 * @Author: wenjiajia
 * @Data: 2018/10/24 1:46 PM
 */
public class Demo2 {
    Nutritions nutrition = new Nutritions.Builder(1,3)
            .calories(3)
            .carbohydrate(4)
            .fat(5)
            .sodium(6)
            .build();


}

/**
 * @Description:
 * @Author: wenjiajia
 * @Data: 2018/10/24 2:08 PM
 */
 class Nutritions{
    // requred arguments

    private int serviceSize;
    private int service;

    //optional arguments

    private int calories;
    private int fat;
    private int sodium;
    private int carbohydrate;
    /**
     * 私有化构造器 通过builder调用
     * @param builder
     */
    private Nutritions(Builder builder){
        this.serviceSize = builder.serviceSize;
        this.service = builder.service;
        this.calories = builder.calories;
        this.fat = builder.fat;
        this.sodium = builder.sodium;
        this.carbohydrate = builder.carbohydrate;
    }

    public static class Builder{
        private int serviceSize;
        private int service;
        private int calories;
        private int fat;
        private int sodium;
        private int carbohydrate;

        public Builder(int serviceSize, int service) {
            this.serviceSize = serviceSize;
            this.service = service;
        }

        public Builder calories(int calories){
            this.calories = calories;
            return this;
        }
        public Builder fat(int fat){
            this.fat = fat;
            return this;
        }
        public Builder sodium(int sodium){
            this.sodium = sodium;
            return this;
        }
        public Builder carbohydrate(int carbohydrate){
            this.carbohydrate = carbohydrate;
            return this;
        }
        public Nutritions build(){
            return new Nutritions(this);
        }
    }
}

