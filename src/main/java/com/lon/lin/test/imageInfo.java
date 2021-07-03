package com.lon.lin.test;

import java.io.Serializable;

public class imageInfo  implements Serializable {

    private String imageType ;
    private String imageUrl ;

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
