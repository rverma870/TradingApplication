import React, { useState, useEffect } from "react";
import useSocketConnection from "../../core/SocketConnection/SocketConnection";
import Security from "../../common/components/Security/Security";
import axios from "axios";
import { stkIDs } from "../../core/base/enums";

function Home() {
  const [security1, setSecurity1] = useState({
    price: 0,
    volume: "",
    id: "",
  });

  const [security2, setSecurity2] = useState({
    price: 0,
    volume: "",
    id: "",
  });
  const [response, setResponse] = useState("");

  const onMessageReceived = (payload) => {
    const payloadData = JSON.parse(payload.body);
    const id = payloadData.id;

    if (id === stkIDs.XYZ) {
      setSecurity1(payloadData);
    } else {
      setSecurity2(payloadData);
    }
  };

  const registerUser = useSocketConnection(onMessageReceived);
  useEffect(() => {
    registerUser();
  }, []);

  const sendRequest = async (URL, payload) => {
    await axios
      .post(URL, payload)
      .then((res) => {
        setResponse(res.data);
      })
      .catch((err) => {
        setResponse("Server Error");
      });
  };

  const placeOrder = (operationType, payload) => {
    let URL;

    if (operationType === "BUY") {
      URL = process.env.REACT_APP_BUY_SECURITY;
    } else if (operationType === "SELL")
      URL = process.env.REACT_APP_SELL_SECURITY;

    sendRequest(URL, payload);
  };

  useEffect(() => {
    setTimeout(() => {
      setResponse("");
    }, 2000);
  }, [response]);

  return (
    <div
      className="bg-light p-5"
      style={{ minHeight: "400vh", backgroundColor: "rgba(0,0,255,.1)" }}
    >
      <h1>The Market</h1>
      <Security
        security1={security1}
        security2={security2}
        placeOrder={placeOrder}
      />
      <p className="fs-1"> {response}</p>
    </div>
  );
}

export default Home;
