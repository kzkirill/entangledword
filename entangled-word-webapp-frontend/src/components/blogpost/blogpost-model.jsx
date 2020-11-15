class BlogPost {
    id;
    authorFullName;
    text;
    lastUpdateAt;

    setId(id) {
        this.id = id;
        return this;
    }
    setAuthorFullName(name) {
        this.authorFullName = name;
        return this;
    }
    setText(text) {
        this.text = text;
        return this;
    }
    setLastUdatedAt(lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
        return this;
    }
}

export default BlogPost