export function getEventsSource(url, onEventCallback){
    const eventSrc = new EventSource(url);
    eventSrc.onerror = function (error) {
        if (eventSrc.readyState === 0) {
            console.log('The stream has been closed by the server.');
            eventSrc.close();
        } else {
            console.error(`EventSource error: ${error}`);
        }
    };
    eventSrc.onmessage = function (userEvent) {
        console.log(`eventSrc.readyState ${eventSrc.readyState}`);
        onEventCallback(JSON.parse(userEvent.data));
    };

}
