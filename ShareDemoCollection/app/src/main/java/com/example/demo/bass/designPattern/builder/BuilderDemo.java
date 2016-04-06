package com.example.demo.bass.designPattern.builder;

/**
 * Builder模式
 * <p/>
 * Builder模式有N个写法，在这里介绍android最常用的写法
 * Created by bass on 16/3/19.
 */
public class BuilderDemo {

    private final int age;
    private final int safeID;
    private final String name;
    private final String address;

    public int getAge() {
        return age;
    }

    public int getSafeID() {
        return safeID;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public static class Builder {
        private int age = 0;
        private int safeID = 0;
        private String name = null;
        private String address = null;

        // 构建的步骤
        public Builder(String name) {
            this.name = name;
        }

        public Builder age(int val) {
            age = val;
            return this;
        }

        public Builder safeID(int val) {
            safeID = val;
            return this;
        }

        public Builder address(String val) {
            address = val;
            return this;
        }

        public BuilderDemo build() { // 构建，返回一个新对象
            return new BuilderDemo(this);
        }
    }

    private BuilderDemo(Builder b) {
        age = b.age;
        safeID = b.safeID;
        name = b.name;
        address = b.address;

    }

//    /**
//     * 使用方法
//     */
//    class Test {
//        //这样写避免写多个构造
//        BuilderDemo builderDemo = new BuilderDemo.Builder("bass").age(18).address("pudong").build();
//    }

}

