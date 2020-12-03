import BPostPreview from "./blogpost-preview.jsx";
import getBlogpostAll from "../../services/blogpost.js";

const { Component } = require("react");

class PostsList extends Component {
    static defaultProps = {
        getPostsFunction: getBlogpostAll
    }

    constructor(props) {
        super(props);
        this.state = { posts: [] };
    }

    componentDidMount() {
        const onNewRecord = post => this.setState(
            previous => ({ posts: previous.posts.concat([post]) }));
        const queryParams = this.props.match ? this.props.match.location.search : null;
        this.props.getPostsFunction(onNewRecord, queryParams);
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