package edu.self.movies.Model;

import java.io.Serializable;

/**
 * Created by lianli on 2017/3/6.
 */

public class Cast implements Serializable {

    private String name;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
