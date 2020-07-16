export class Post {
    id: String;
    author: String;
    text: String;
    title: String;
    created: Date;
    updated: Date;
    constructor(id: String, author: String, text: String, title: String,created: Date,updated: Date) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.title = title;
        this.created = created;
        this.updated = updated;
    }
}