package com.greymax.vkplayer.api;

public class ApiMethods {

    public static class Audio {
        public static final String GET = "audio.get";
        public static final String SEARCH = "audio.search";
        public static final String GET_RECOMMENDATIONS = "audio.getRecommendations";
        public static final String EDIT = "audio.edit";
        public static final String ADD = "audio.add";
        public static final String DELETE = "audio.delete";
    }

    public static class Friends {
        public static final String GET = "friends.get";
    }

    public static class Status {
        public static final String GET = "status.get";
        public static final String SET = "status.set";
    }

    public static class Wall {
        public static final String POST = "wall.post";
    }

    public static class Video {
        public static final String SEARCH = "video.search";
    }
}
