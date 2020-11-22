import { Redirect } from 'react-router-dom';
import { loginHelper } from '../../global/authentication.js';
import { create, getBlogpost } from '../../services/blogpost.js'
import { clusterize } from '../../services/intellexer.js'
const { Component } = require("react")

const modeUnknown = 'unknown';
const modeNew = 'new';
const modeEdit = 'edit';
const modeReadonly = 'readonly';
class PostForm extends Component {
    static defaultProps = {
        key: modeUnknown,
        refreshTags: function () { console.log(`refreshTags is not implemented`) }
    }

    constructor(props) {
        super(props);
        this.state = {
            mode: props.key,
            title: '',
            text: '',
            tags: []
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.extractTags = this.extractTags.bind(this);
        this.getTags = this.getTags.bind(this);
    }

    componentDidMount() {
        const postID = this.props.match ? this.props.match.match.params.ID : null;
        const loggedID = loginHelper.getLoggedIn();
        if (postID) {
            getBlogpost(postID).then(blogpost => {
                const mode = blogpost.author === loggedID ? modeEdit : modeReadonly;
                this.setState(() => ({
                    mode: mode,
                    title: blogpost.title,
                    text: blogpost.text,
                    tags: blogpost.tags
                }));
            });
        } else {
            this.setState(() => ({
                mode: modeNew,
                title: '',
                text: '',
                tags: []
            }));
        }
    }

    getTags() {
        clusterize();//.then(response => console.log(response));
    }

    handleSubmit(event) {
        const toSave = {
            text: this.state.text,
            title: this.state.title,
            userID: loginHelper.getLoggedIn(),
            lastUpdateAt: Date.now(),
            tags: this.state.tags
        };
        create(toSave)
            .then(response => {
                this.props.refreshTags();
                console.log(`Status : ${response.status} Created with ID : ${response.data.id}`);
            })
            .catch(exc => console.log(exc));
        event.preventDefault();
    }

    handleChange(event) {
        const fieldValue = event.target.id === 'tags' ? this.extractTags(event.target.value) : event.target.value
        this.setState({ [event.target.id]: fieldValue });
    }

    extractTags(fieldValue) {
        return fieldValue.split(',');
    }

    buildForm() {
        const tagsValue = this.state.tags.join(',');
        const submitVisibilityClass = this.state.mode === modeReadonly ? ' is-hidden' : '';
        const readonlyProp = this.state.mode === modeReadonly;
        return (
            <form onSubmit={this.handleSubmit}>
                <div className="field">
                    <label className="label">Title</label>
                    <div className="control">
                        <input id='title' value={this.state.title} onChange={this.handleChange} type='text' className="input" placeholder="Title" readOnly={readonlyProp} />
                    </div>
                </div>
                <div className="field">
                    <label className="label">Text</label>
                    <div className="control">
                        <textarea id='text' value={this.state.text} onChange={this.handleChange} className="textarea" placeholder="Type here" readOnly={readonlyProp} ></textarea>
                    </div>
                </div>
                <div className="field">
                    <label className="label">Tags</label>
                    <div className="control field is-grouped">
                        <input id='tags' value={tagsValue} onChange={this.handleChange} type='text' className="input" placeholder="Add tags separated with commas" readOnly={readonlyProp} />
                        <button onClick={this.getTags} className="control button is-link is-light">Get tags</button>
                    </div>
                </div>

                <div className={"field is-grouped" + submitVisibilityClass}>
                    <div className="control">
                        <input type="submit" value="Submit" className="button is-link"></input>
                    </div>
                </div>
            </form>
        );

    }

    render() {
        if (this.state.mode === modeUnknown) {
            return (<p className="control is-loading">Loading...</p>);
        }
        const loggedIn = loginHelper.getLoggedIn();
        console.log(`Logged IN ${loggedIn}`);
        if (loggedIn === null && this.state.mode === modeNew) {
            return (<Redirect to="/login" />);
        } else {
            return this.buildForm();
        }
    }
}

export default PostForm;
export { modeUnknown, modeNew, modeEdit, modeReadonly };