package model;

public class Post {
    private String writer;
    private String title;
    private String contents;

    public Post(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    // Getter 메소드들
    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
