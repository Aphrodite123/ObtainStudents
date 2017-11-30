package com.aphrodite.obtainstudents.http.model;

import java.util.List;

/**
 * Created by Aphrodite on 2017/5/11.
 */

public class GetStudentsRsp extends BaseRsp {
    public List<Data> data;

    public class Data {
        public String id;
        public String name;
        public String sex;
        public String age;

        @Override
        public String toString() {
            String str = "Enter toString method,Student: " + "[id: " + id
                    + " name: " + name + " sex: " + sex + " age: " + age + "]";
            return str;
        }
    }

}
