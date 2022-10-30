import React from "react";

function StockOperation({ quantity, name, handleInput, handleOperation }) {
  return (
    <div>
      <input
        className="form-control mt-4"
        type="number"
        placeholder="Number of units"
        aria-label="default input example"
        name={name}
        value={quantity}
        onChange={handleInput}
      />
      <button
        id="buyButton"
        className="m-3 btn btn-success"
        name={name}
        onClick={handleOperation}
      >
        Buy
      </button>
      <button
        id="sellButton"
        className="m-3 btn btn-danger"
        name={name}
        onClick={handleOperation}
      >
        Sell
      </button>
    </div>
  );
}

export default React.memo(StockOperation);
