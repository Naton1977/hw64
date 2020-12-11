package org.example;

public class Posts {
    int id;
    String postAuthor;
    String publicationDate;
    String postName;

    public Posts(int id, String postAuthor, String publicationDate, String postName) {
        this.id = id;
        this.postAuthor = postAuthor;
        this.publicationDate = publicationDate;
        this.postName = postName;
    }

    public int getId() {
        return id;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getPostName() {
        return postName;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "id='" + id + '\'' +
                ", postAuthor='" + postAuthor + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", postName='" + postName + '\'' +
                '}';
    }
}
