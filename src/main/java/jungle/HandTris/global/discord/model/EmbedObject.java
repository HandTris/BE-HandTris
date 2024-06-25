package jungle.HandTris.global.discord.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmbedObject {


    private final List<Field> fields = new ArrayList<>();

    private String title;

    private String description;

    private String url;

    private Color color;

    private Footer footer;

    private Thumbnail thumbnail;

    private Image image;

    private Author author;

    public String getTitle() {
        return title;
    }

    public EmbedObject setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EmbedObject setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public EmbedObject setUrl(String url) {
        this.url = url;
        return this;
    }

    public Color getColor() {
        return color;
    }

    public EmbedObject setColor(Color color) {
        this.color = color;
        return this;
    }

    public Footer getFooter() {
        return footer;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public EmbedObject setThumbnail(String url) {
        this.thumbnail = new Thumbnail(url);
        return this;
    }

    public Image getImage() {
        return image;
    }

    public EmbedObject setImage(String url) {
        this.image = new Image(url);
        return this;
    }

    public Author getAuthor() {
        return author;
    }

    public List<Field> getFields() {
        return fields;
    }

    public EmbedObject setFooter(String text, String icon) {
        this.footer = new Footer(text, icon);
        return this;
    }

    public EmbedObject setAuthor(String name, String url, String icon) {
        this.author = new Author(name, url, icon);
        return this;
    }

    public EmbedObject addField(String name, String value, boolean inline) {
        this.fields.add(new Field(name, value, inline));
        return this;
    }
}
