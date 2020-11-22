import { getEventsSource } from "./events-source";
import { rootUrl } from "./props";

const endPoint = '/tag';
export function getTags(tagReceived) {
    getEventsSource(rootUrl + endPoint, tagReceived);
}