import {Post} from '../model/post';

export const STATE_NEW = 'NEW_POST';
export const STATE_READ_ONLY = 'READ_ONLY_POST';

export class PostState{
    post: Post;
    mode: String;

    static newInstance(mode:String,post:Post){
        return new PostState(mode,post);
    }

    static newFromPostReadOnly(post:Post){
        return new PostState(STATE_READ_ONLY,post);
    }

    private constructor(mode:String,post:Post){
        this.mode = mode;
        this.post = post;
    }
}