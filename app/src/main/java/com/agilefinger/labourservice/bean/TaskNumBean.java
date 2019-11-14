package com.agilefinger.labourservice.bean;

public class TaskNumBean {


    /**
     * code : 0
     * data : {"mine":{"doing":1,"wait":0,"stop":0,"finish":1},"send":{"doing":1,"wait":9,"stop":0,"finish":3}}
     * message : 成功！
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * mine : {"doing":1,"wait":0,"stop":0,"finish":1}
         * send : {"doing":1,"wait":9,"stop":0,"finish":3}
         */

        private MineBean mine;
        private SendBean send;

        public MineBean getMine() {
            return mine;
        }

        public void setMine(MineBean mine) {
            this.mine = mine;
        }

        public SendBean getSend() {
            return send;
        }

        public void setSend(SendBean send) {
            this.send = send;
        }

        public static class MineBean {
            /**
             * doing : 1
             * wait : 0
             * stop : 0
             * finish : 1
             */

            private int doing;
            private int wait;
            private int stop;
            private int finish;

            public int getDoing() {
                return doing;
            }

            public void setDoing(int doing) {
                this.doing = doing;
            }

            public int getWait() {
                return wait;
            }

            public void setWait(int wait) {
                this.wait = wait;
            }

            public int getStop() {
                return stop;
            }

            public void setStop(int stop) {
                this.stop = stop;
            }

            public int getFinish() {
                return finish;
            }

            public void setFinish(int finish) {
                this.finish = finish;
            }
        }

        public static class SendBean {
            /**
             * doing : 1
             * wait : 9
             * stop : 0
             * finish : 3
             */

            private int doing;
            private int wait;
            private int stop;
            private int finish;

            public int getDoing() {
                return doing;
            }

            public void setDoing(int doing) {
                this.doing = doing;
            }

            public int getWait() {
                return wait;
            }

            public void setWait(int wait) {
                this.wait = wait;
            }

            public int getStop() {
                return stop;
            }

            public void setStop(int stop) {
                this.stop = stop;
            }

            public int getFinish() {
                return finish;
            }

            public void setFinish(int finish) {
                this.finish = finish;
            }
        }
    }
}
