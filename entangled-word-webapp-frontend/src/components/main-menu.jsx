import { Component } from "react";
import { Link, useHistory } from 'react-router-dom';
import { loginHelper } from "../global/authentication";

class MainMenu extends Component {

    constructor(props) {
        super(props);
        this.logout = this.logout.bind(this);
    }

    logout() {
        loginHelper.logout();
        this.props.loggedStateChanged();
    }

    render() {
        const loggedIn = this.props.user !== null;
        return (
            <nav className="navbar tile  box" role="navigation" aria-label="main navigation">
                <div className="navbar-brand">
                    <span className="navbar-item" href="#">
                        Entangled Word
                    </span>
                    <a role="button" className="navbar-burger burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
                        <span aria-hidden="true"></span>
                        <span aria-hidden="true"></span>
                        <span aria-hidden="true"></span>
                    </a>
                </div>
                <div id="navbarBasicExample" className="navbar-menu">
                    <div className="navbar-start">
                        <Link to="/feed" className="navbar-item">
                            Main Feed
                        </Link>
                        <Link to="/new" className="navbar-item">
                            New Post
                        </Link>
                    </div>
                    <div className="navbar-end">
                        <div className="navbar-item">
                            <div className="buttons">
                                {
                                    loggedIn ?
                                        (<span onClick={this.logout} className="button is-light ">
                                            <strong>Log out</strong>
                                        </span>)
                                        : (<Link to="/login" className="button is-primary">
                                            Log in
                                        </Link>)
                                }


                            </div>
                        </div>
                    </div>
                </div>
            </nav>
        );
    }
}

export default MainMenu;