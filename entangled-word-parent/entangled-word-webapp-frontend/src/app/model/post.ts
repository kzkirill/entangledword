export class Post {
    id: String;
    author: String;
    text: String;
    title: String;
    created: Date;
    updated: Date;
    protected replies: Post[];

    static emptyInstance(){
        return new Post('','','','',null,null);
    }
    static newInstance(id: String, author: String, text: String, title: String, created: Date, updated: Date): Post {
        return new Post(id, author, text, title, created, updated);
    }

    private constructor(id: String, author: String, text: String, title: String, created: Date, updated: Date) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.title = title;
        this.created = created;
        this.updated = updated;
        this.replies = [];
    }

    addReply(reply: Post) {
        this.replies.push(reply);
    }

    getReplies(): Array<Post> {
        return this.replies.slice(0, this.replies.length - 1);
    }
}
