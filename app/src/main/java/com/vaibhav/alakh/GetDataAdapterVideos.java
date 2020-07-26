package com.vaibhav.alakh;

public class GetDataAdapterVideos {

    public String Url;
    public String Title;
    public String Description;
    public String Thumbnail;
    public String Category;
    public String Isvideo;


    //For Video URL
    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }

    //For Title
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title= title;
    }

    //For Description
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    //For Header Image
    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.Thumbnail = thumbnail;
    }

    //For Category
    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    //For Isvideo
    public String getIsvideo() {
        return Isvideo;
    }

    public void setIsvideo(String isvideo) {
        this.Isvideo = isvideo;
    }


}
