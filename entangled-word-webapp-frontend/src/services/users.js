import axios from 'axios';
import { rootUrl } from './props';

const endPoint = '/user/';

export default function getUserAll() {
    const url = rootUrl + endPoint;
    return axios.get(url);
}

export function getUser(ID) {
    const url = rootUrl + endPoint + `/${ID}`;
    return axios.get(url);
}

export function create(newUser) {
    const url = rootUrl + endPoint;
    return axios.post(url, newUser);
}