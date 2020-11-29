import axios from 'axios';
import { getEventsSource } from './events-source';
import { rootUrl } from './props';
import { getUser } from './users';

const endPoint = '/blogpost/';

export default function getBlogpostAll(blogpostReceived) {
    getEventsSource(rootUrl + endPoint, blogpostReceived);
}

export function getBlogpost(ID) {
    const promise = axios.get(rootUrl + endPoint + ID);
    const full = promise.then(result => {
        const blogpost = result.data;
        return getUser(blogpost.author)
            .then(userResult => {
                const user = userResult.data;
                blogpost.userFullname = `${user.name.title} ${user.name.first} ${user.name.last}`;
                blogpost.userPicture = user.pictureURL;
                return blogpost;
            });
    });

    return full;
}

export function create(newPost) {
    const url = rootUrl + endPoint;
    return axios.post(url, newPost);
}

export function update(updatePost) {
    const url = rootUrl + endPoint + updatePost.id;
    return axios.put(url, updatePost);
}