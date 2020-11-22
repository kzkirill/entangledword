import { Redirect } from "react-router-dom";
import { loginHelper } from "../../global/authentication";
import getUserAll from "../../services/users";

const { Component } = require("react");

class UsersList extends Component {

    constructor(props) {
        super(props);

        this.state = {
            didLogIn: false,
            users: []
        };
        this.renderOneUser = this.renderOneUser.bind(this);
        this.setUser = this.setUser.bind(this);
    }

    componentDidMount() {
        getUserAll(data => {
            this.setState(function (previous) {
                return { users: previous.users.concat([data]) };
            });
        });
    }

    setUser(user) {
        loginHelper.setLoggedIn(user);
        this.props.loggedStateChanged();
        this.setState(() => ({ didLogIn: true }));
    }

    renderList(user) {
        return (
            <div className="card">
                <div className="card-content">
                    <div className="media">
                        <div className="media-left">
                            <figure className="image">
                                <img src={user.pictureURL} alt="Profile" /></figure>
                        </div>
                        <div className="media-content">
                            <p className="title is-4">{user.name.title} {user.name.first} {user.name.last}</p>
                            <p className="subtitle is-6">{user.email}</p>
                        </div>
                    </div>
                    <div className="content">
                        {user.username}
                    </div>
                    <button onClick={() => this.setUser(user)} className="control button is-link is-light">Login as me</button>
                </div>
            </div>
        );
    }

    renderOneUser(user) {
        return this.state.didLogIn ? (<Redirect to="/" />) : this.renderList(user);
    }

    render() {
        const userCards = this.state.users.map(user => this.renderOneUser(user));
        return (<div>
            <div className="title is-5">Choose user</div>
            {userCards}
        </div>);
    }
}

export { UsersList }