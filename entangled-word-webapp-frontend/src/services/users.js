import axios from 'axios';
import { getEventsSource } from './events-source';
import { rootUrl } from './props';

const endPoint = '/user';

export default function getUserAll(userReceived) {
    getEventsSource(rootUrl + endPoint, userReceived);
}

export function getUser(ID) {
    const url = rootUrl + endPoint + `/${ID}`;
    return axios.get(url);
}

export function create(newUser) {
    const url = rootUrl + endPoint;
    return axios.post(url, newUser);
}