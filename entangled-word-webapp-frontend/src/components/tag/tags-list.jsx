import { Component } from "react";
import { Link } from "react-router-dom";

class TagsList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            selected: []
        };
        this.tagClicked = this.tagClicked.bind(this);
    }

    selectionClass(tagValue) {
        return this.state.selected.indexOf(tagValue) > -1 ? " is-info" : " is-light";
    }

    tagClicked(value) {
        const selected = this.state.selected;
        const position = selected.indexOf(value);
        if (position > -1) {
            selected.splice(position, 1);
        } else {
            selected.push(value);
        }
        console.log(`Selected tags ${selected}`);
        this.setState(() => selected);
    }

    buildButton() {
        if (this.state.selected.length === 0) {
            return (
                <span className="button" disabled>Show Posts</span>
            );
        } else {
            return (
                <Link className="button"
                    to={{
                        pathname: "/feed/tags",
                        search: "?tags=" + this.state.selected
                    }}
                >Show Posts</Link>
            );
        };
    }

    render() {
        return (<div className='container'>
            <p className="title">Tags</p>
            <div className='tags'>
                {this.props.tags.map(tag => (<span
                    onClick={() => this.tagClicked(tag)}
                    key={tag}
                    className={"tag" + this.selectionClass(tag)}>
                    {tag}
                </span>))}
                {this.buildButton()}
            </div>
        </div>)
    }

}

export { TagsList };