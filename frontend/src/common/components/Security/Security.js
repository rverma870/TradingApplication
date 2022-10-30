import React, { useCallback, useState } from "react";
import PriceRenderer from "../PriceRenderer/PriceRenderer";
import StockOperation from "../StockOperation/StockOperation";
import { operationType } from "../../../core/base/enums";

function Security({ security1, security2, placeOrder }) {
  const { price: price1, id: id1 } = security1;
  const { price: price2, id: id2 } = security2;

  const [quantity1, setQuantity1] = useState("");
  const [quantity2, setQuantity2] = useState("");

  const handleInput = useCallback((e) => {
    const name = e.target.name;
    const value = e.target.value;
    if (name === "id1") {
      setQuantity1(value);
    } else {
      setQuantity2(value);
    }
  }, []);

  const handleOperation = (e) => {
    const name = e.target.name;
    let payload = {};
    if (name === "id1") {
      payload = {
        price: price1,
        volume: quantity1,
        id: id1,
      };
    } else {
      payload = {
        price: price2,
        volume: quantity2,
        id: id2,
      };
    }

    let opType;

    if (e.target.id === "buyButton") opType = operationType.BUY;
    else if (e.target.id === "sellButton") opType = operationType.SELL;

    placeOrder(opType, payload);
    setQuantity1("");
    setQuantity2("");
  };

  return (
    <div className="container p-5">
      <div className="row d-flex justify-content-center text-light">
        <div className="col-5 bg-secondary p-5 pt-3 m-2 rounded-pill">
          <h1>XYZ</h1>
          <PriceRenderer price={price1} />
          <StockOperation
            quantity={quantity1}
            name={"id1"}
            handleInput={handleInput}
            handleOperation={handleOperation}
          />
        </div>

        <div className="col-5 bg-secondary p-5 pt-3 m-2 rounded-pill">
          <h1>ABC</h1>
          <PriceRenderer price={price2} />
          <StockOperation
            quantity={quantity2}
            name={"id2"}
            handleInput={handleInput}
            handleOperation={handleOperation}
          />
        </div>
      </div>
    </div>
  );
}

export default Security;
