import Stomp from "stompjs";
import SockJs from "sockjs-client";

function useSocketConnection(onMessageReceived) {
  let stompClient = undefined;

  const registerUser = () => {
    const socket = new SockJs(`${process.env.REACT_APP_SOCKET_URL}`);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    return stompClient;
  };

  const onConnected = (frame) => {
    stompClient.subscribe(
      `${process.env.REACT_APP_SOCKET_TOPIC_GET_LIVE_PRICE}`,
      onMessageReceived
    );
  };

  const onError = (err) => {
    console.log(err);
  };

  return registerUser;
}

export default useSocketConnection;
