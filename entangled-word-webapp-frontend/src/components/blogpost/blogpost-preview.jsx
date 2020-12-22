import { authorParamName, byQueryURL } from "../../services/blogpost";

const { Component } = require("react");

class BPostPreview extends Component {

    textPreview(text) {
        return text.substr(0, 25) + '...';
    }
    render() {
        const post = this.props.post;
        const lastUpdatedStr = post.updated;
        const postURL = `/details/${post.id}`;
        const findByAuthorURl = byQueryURL + "?" + authorParamName + "=" + post.author;
        return (
            <div className="card">
                <div className="card-content">
                    <div className="media">
                        <div className="media-left">
                            <figure className="image is-48x48">
                                <img src={post.userPicture} alt="Profile" /></figure>
                        </div>
                        <div className="media-content">
                            <p className="title is-4">{post.title}</p>
                            <a className="navbar-item subtitle is-6" href={findByAuthorURl}>{post.userFullname} </a>
                            <time dateTime={lastUpdatedStr}>{lastUpdatedStr}</time>
                        </div>
                    </div>
                    <div className="content">
                        {this.textPreview(post.text)}
                        <br />
                        <a href={postURL}>See more</a>.
                    </div>
                </div>
            </div>

        );
    }
}

export default BPostPreview;