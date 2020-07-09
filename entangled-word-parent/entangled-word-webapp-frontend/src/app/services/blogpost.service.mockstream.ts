import { Post } from "../model/post";

export class MockBlogpostStream {

    public run() {
        for (let i = 0; i < 10; i++) {
            let event = new MessageEvent('type', {
                data: JSON.stringify(new Post(i.toString(), 'Author', 'Ipsum solem'))
            });

            this.eventsConsumer.onmessage(event);
        }
    }
    constructor(private eventsConsumer: EventSource) {

    }
}