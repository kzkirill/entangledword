import BPostPreview from "./blogpost-preview.jsx";
import getBlogpostAll from "../../services/blogpost.js";

const { Component } = require("react");

class PostsList extends Component {
    constructor(props) {
        super(props);
        this.state = { posts: [] };
        this.updateFromNewList = this.updateFromNewList.bind(this);
    }

    updateFromNewList(newPosts) {
        this.setState({ posts: [] });
        newPosts.forEach(post =>
            this.setState(previous => ({ posts: previous.posts.concat([post]) })));
    }

    componentDidMount() {
        getBlogpostAll()
            .then(result => this.updateFromNewList(result));
    }

    render() {
        const items = this.state.posts.map(item => < BPostPreview key={item.id} post={item} />)
        return (<div>
            <h5>Feed</h5>
            <ul>{items}</ul>
        </div>)
    }
}

export default PostsList;