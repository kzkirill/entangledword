export class Post {
    id: String;
    author: String;
    text: String;
    constructor(id: String, author: String, text: String) {
        this.id = id;
        this.author = author;
        this.text = text;
    }
}