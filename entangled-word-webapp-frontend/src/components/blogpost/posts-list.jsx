import BPostPreview from "./blogpost-preview.jsx";
import getBlogpostAll from "../../services/blogpost.js";

const { Component } = require("react");

class PostsList extends Component {
    constructor(props) {
        super(props);
        this.state = { posts: [] };
    }
    componentDidMount() {
        getBlogpostAll(post => this.setState(
            previous => ({ posts: previous.posts.concat([post]) }))
        );
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