import axios from 'axios';
import { getEventsSource } from './events-source';
import { rootUrl } from './props';
import { getUser } from './users';

const endPoint = '/blogpost/';

function byUdatedDesc(post1, post2) {
    if (post1.lastUpdateAt < post2.lastUpdateAt) {
        return 1;
    }
    if (post1.lastUpdateAt > post2.lastUpdateAt) {
        return -1;
    }
    return 0;
}

export default function getBlogpostAll(blogpostReceived) {
    getEventsSource(rootUrl + endPoint, blogpostReceived);
}

export function getBlogpost(ID) {
    const promise = axios.get(rootUrl + endPoint + ID);
    const full = promise.then(result => {
        const blogpost = result.data;
        return getUser(blogpost.userID)
            .then(userResult => {
                const user = userResult.data;
                blogpost.userFullname = `${user.name.title} ${user.name.first} ${user.name.last}`;
                blogpost.userPicture = user.picture.thumbnail;
                return blogpost;
            });
    });

    return full;
}

export function create(newPost) {
    const url = rootUrl + endPoint;
    return axios.post(url, newPost);
}

function onlyUnique(value, index, self) {
    return self.indexOf(value) === index;
}

export function getTags() {
    const url = rootUrl + endPoint;
    return axios.get(url)
        .then(result => result.data)
        .then(allPosts => ['asdasd', 'asdasd'])//allPosts.map(one => one.tags))
        .then(arrayOfArrays => arrayOfArrays.flat()
            .filter(onlyUnique));
}