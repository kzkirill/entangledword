const { Component } = require("react");

class CurrentUser extends Component {

    renderUser(user) {
        return (
            <div className="card">
                <div className="title">Current user</div>
                <div className="card-content">
                    <div className="media">
                        <div className="media-left">
                            <figure className="image is-24x24">
                                <img src={user.pictureURL} alt="Profile" /></figure>
                        </div>
                        <div className="media-content">
                            <p className="subtitle is-6">{user.name.title} {user.name.first} {user.name.last}</p>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    render() {
        const user = this.props.user;
        return user === null ? (<div>Not logged in</div>) : this.renderUser(user);
    }

}

export default CurrentUser;