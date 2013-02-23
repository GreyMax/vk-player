package com.greymax.vkplayer.api.params;

public enum Templates {

    AUDIO_GET(0L),
    AUDIO_SEARCH(1L),
    AUDIO_EDIT(2L),
    AUDIO_ADD(3L),
    AUDIO_DELETE(4L);

    public static final int DEFAULT_COUNT = 200;
    public static final String PROP_OWNER = "";

    private Long id;

    Templates(Long id) {
        this.id = id;
    }

}
