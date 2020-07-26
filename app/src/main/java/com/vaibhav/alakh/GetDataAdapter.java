package com.vaibhav.alakh;

public class GetDataAdapter {

    public String VideoUrl;
    public String Title;
    public String Description;
    public String Image;
    public String PostID;


    //For Video URL
    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.VideoUrl = videoUrl;
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
    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    //For Post ID
    public String getPostID() {
        return PostID;
    }

    public void setPostID(String postID) {
        this.PostID = postID;
    }


}
