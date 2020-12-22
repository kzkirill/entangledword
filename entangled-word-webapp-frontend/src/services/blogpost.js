import axios from 'axios';
import { getEventsSource } from './events-source';
import { rootUrl } from './props';
import { getUser } from './users';

const endPoint = '/blogpost/';
const backendQuerySuffix = 'search';
export const byQueryURL = "/feed/byquery";
export const tagsParamName = 'tags';
export const authorParamName = 'author';

export default function getBlogpostAll(blogpostReceived) {
    getEventsSource(rootUrl + endPoint, blogpostReceived);
}

export function getByQuerySearch(onResultReceived, searchWithQueryParams) {
    getEventsSource(rootUrl + endPoint + backendQuerySuffix + searchWithQueryParams , onResultReceived);
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