"use client";
import * as React from "react";
import classNames from "classnames";
import Accordion from "@mui/material/Accordion";
import AccordionSummary from "@mui/material/AccordionSummary";
import AccordionDetails from "@mui/material/AccordionDetails";
import Typography from "@mui/material/Typography";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import ArrowDownwardIcon from "@mui/icons-material/ArrowDownward";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import Modal from "@mui/material/Modal";

import { useEffect, useState } from "react";
import { Key } from "@mui/icons-material";
import {
  duration,
  FormControlLabel,
  Radio,
  RadioGroup,
  TextField,
} from "@mui/material";

import { Box, ThemeProvider, createTheme } from "@mui/system";
import ControlledCheckbox from "./components/ControlledCheckbox";

export default function Home() {
  const theme = createTheme({
    palette: {
      background: {
        gray: "#009688",
      },
    },
  });

  const defURL = "http://localhost:8000/api";

  interface transaction {
    transactionId: string;
    accountId: string;
    amount: number;
    currency: string;
    direction: string;
    description: string;
  }

  interface sendTransaction {
    accountId: string;
    amount: number;
    currency: string;
    direction: string;
    description: string;
  }

  interface newAccountForm {
    customerId: string;
    country: string;
    currencies: string[];
  }

  interface balance {
    accountId: string;
    currency: string;
    totalAmount: number;
  }

  interface fullAccount {
    accountId: string;
    customerId: string;
    country: string;
    balances: balance[];
  }

  const allCurrencies = ["EUR", "USD", "SEK", "GBP"];

  const [transactions, setTransactions] = useState<transaction[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [expanded, setExpanded] = useState<string | false>(false);
  const [accountIds, setAccountIds] = useState<string[]>([]);
  const [newAccountModal, setNewAccountModal] = useState<boolean>(false);
  const [newTransactionModal, setNewTransactionModal] =
    useState<boolean>(false);
  const [filteredCurrencies, setFilteredCurrencies] = useState<string[]>([]);
  const [newCustomerId, setNewCustomerId] = useState<string>("");
  const [newCountry, setNewCountry] = useState<string>("");

  const [transactionAccountId, setTransactionAccountId] = useState<string>("");
  const [transactionAmount, setTransactionAmount] = useState<number>(0.0);
  const [transactionCurrency, setTransactionCurrency] = useState<string>("");
  const [transactionDirection, setTransactionDirection] =
    useState<string>("IN");
  const [transactionDescription, setTransactionDescription] =
    useState<string>("");

  const [newAccountResponseMessage, setNewAccountResponseMessage] =
    useState<string>("");

  const [newFullAccount, setNewFullAccount] = useState<fullAccount>();

  const [curBalances, setCurBalances] = useState<balance[]>();

  function handleGenreFilterChange(cur: string) {
    if (filteredCurrencies.includes(cur)) {
      if (filteredCurrencies.length === 1) {
        filteredCurrencies.shift();
      } else {
        const index = filteredCurrencies.indexOf(cur);
        filteredCurrencies.splice(index, index);
      }
      //Remember the changes
      setFilteredCurrencies(filteredCurrencies);
    } else {
      filteredCurrencies.push(cur);
      setFilteredCurrencies(filteredCurrencies);
    }
    console.log(filteredCurrencies);
  }

  async function updateTransactions() {
    const res = await fetch(`${defURL}/allTransactions`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });

    res.json().then((result) => {
      const newTransactions: transaction[] = [];

      result.forEach((element: transaction) => {
        newTransactions.push(element);
      });

      setTransactions(newTransactions);
    });

    const res2 = await fetch(`${defURL}/allAccountIds`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });

    res2.json().then((result) => {
      const allAccountIds: string[] = result;
      setAccountIds(allAccountIds);
    });

    setIsLoading(false);
  }

  function getAllCurrenciesFromFullAccount(account: fullAccount) {
    if (!account) {
      return [];
    }
    let curCurrencies: string[] = [];
    account.balances.forEach((element: balance) => {
      curCurrencies.push(element.currency);
    });

    return curCurrencies;
  }

  const handleNewTransactionModalOpen = async (accountId: string) => {
    setTransactionAccountId(accountId);
    setNewTransactionModal(true);
    const req1 = `${defURL}/account?accountId=${accountId}`;
    // const req2 = `${defURL}/transaction?accountId=${accountId}`

    const res = await fetch(req1, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });
    if (res.status === 404) {
      return;
    }

    res.json().then((result) => {
      const receivedAccount: fullAccount = result;
      setNewFullAccount(receivedAccount);
      setTransactionCurrency(result.balances[0].currency);
      setCurBalances(result.balances);
    });
  };

  const handleAccountCreate = async () => {
    if (filteredCurrencies.length === 0) {
      setNewAccountResponseMessage("NO CURRENCIES SELECTED TRY AGAIN");
      console.log("NO CURRENCIES SELECTED TRY AGAIN");
      return;
    }

    if (newCustomerId.length === 0) {
      setNewAccountResponseMessage("NO CUSTOMER ID INSERTED TRY AGAIN");
      console.log("NO CUSTOMER ID INSERTED TRY AGAIN");
      return;
    }

    if (newCountry.length === 0) {
      console.log("NO COUNTRY INSERTED TRY AGAIN");
      setNewAccountResponseMessage("NO COUNTRY INSERTED TRY AGAIN");
      return;
    }

    const submitData: newAccountForm = {
      customerId: newCustomerId,
      country: newCountry,
      currencies: filteredCurrencies,
    };

    const reqUrl = `${defURL}/publishAccount`;
    const res = await fetch(reqUrl, {
      method: "POST",
      body: JSON.stringify(submitData),
      headers: {
        "Content-Type": "application/json",
      },
    });

    res.text().then((result) => {
      setNewAccountResponseMessage(result);
      setNewAccountModal(false);
      location.reload();
    });
  };

  const handleTransaction = async () => {
    const submitData: sendTransaction = {
      accountId: transactionAccountId,
      amount: transactionAmount,
      currency: transactionCurrency,
      direction: transactionDirection,
      description: transactionDescription,
    };

    if (transactionAmount === 0.0) {
      setNewAccountResponseMessage("AMOUNT CANT BE 0");
      console.log("AMOUNT CANT BE 0");
      return;
    }

    if (transactionCurrency === "") {
      setNewAccountResponseMessage("NO CURRENCY SELECTED");
      console.log("NO CURRENCY SELECTED");
      return;
    }

    if (transactionDirection === "") {
      console.log("NO DIRECTION SELECTED");
      setNewAccountResponseMessage("NO DIRECTION SELECTED");
      return;
    }
    if (transactionDescription === "") {
      console.log("NO DESCRIPTION");
      setNewAccountResponseMessage("NO DESCRIPTION");
      return;
    }

    const reqUrl = `${defURL}/publishTransaction`;
    const res = await fetch(reqUrl, {
      method: "POST",
      body: JSON.stringify(submitData),
      headers: {
        "Content-Type": "application/json",
      },
    });

    res.json().then((result: transaction) => {
      if (result.accountId === null) {
        setNewAccountResponseMessage(result.description);
      } else {
        setNewAccountResponseMessage("OK");
        location.reload()
      }
    });
  };

  useEffect(() => {
    setIsLoading(true);
    updateTransactions();
  }, []);

  return (
    <>
      {/* <div className="w-screen h-screen flex justify-center items-center">
        
      </div> */}
      <div className="flex w-screen h-[10vh] pb-12 px-12"></div>
      <div className="flex w-screen h-[90vh] pb-12 px-12 justify-center items-center">
        <Modal
          className="w-screen h-screen flex justify-center items-center px-36 py-20"
          open={newAccountModal}
          onClose={() => {
            setNewAccountModal(false);
            setNewAccountResponseMessage("");
          }}
        >
          <div className="flex flex-col bg-white w-full h-full rounded-lg outline-none">
            <div className="flex p-12">
              <div className="flex w-1/2">
                <div className="flex flex-col px-16">
                  {allCurrencies.map((currency) => (
                    <div key={Math.random()} className="flex items-center">
                      <div
                        onClick={() => {
                          handleGenreFilterChange(currency);
                        }}
                      >
                        <ControlledCheckbox />
                      </div>

                      <Typography variant="body2">{currency}</Typography>
                    </div>
                  ))}
                </div>
              </div>
              <div className="w-1/2 flex flex-col justify-evenly ">
                <TextField
                  required
                  id="outlined-required"
                  label="CustomerId"
                  placeholder="customerId"
                  onChange={(e) => {
                    setNewCustomerId(e.target.value);
                  }}
                />
                <TextField
                  required
                  id="outlined-required"
                  label="Country"
                  placeholder="Country of origin"
                  onChange={(e) => {
                    setNewCountry(e.target.value);
                  }}
                />
              </div>
            </div>

            <div className="flex flex-col justify-center items-center">
              <div
                className="bg-blue-600 p-4 text-white rounded-lg hover:bg-blue-800 cursor-pointer"
                onClick={handleAccountCreate}
              >
                create new account
              </div>
              {newAccountResponseMessage}
            </div>
          </div>
        </Modal>
        <Modal
          className="w-screen h-screen flex justify-center items-center px-12 py-10"
          open={newTransactionModal}
          onClose={() => {
            setNewTransactionModal(false);
            console.log(newFullAccount);
          }}
        >
          <div className="bg-white w-full h-full rounded-lg outline-none">
            {!newFullAccount ? (
              <></>
            ) : (
              <div className="flex w-full h-full">
                <div className="flex flex-col p-8 w-1/2 items-center justify-center">
                <div className="mb-2">{transactionAccountId}</div>
                  <div className="flex mb-4">
                    <div className="mx-4">
                      <RadioGroup
                        value={transactionCurrency}
                        onChange={(
                          event: React.ChangeEvent<HTMLInputElement>
                        ) => {
                          setTransactionCurrency(event.target.value);
                          console.log(transactionCurrency);
                        }}
                      >
                        {getAllCurrenciesFromFullAccount(newFullAccount).map(
                          (curCurrency: string) => (
                            <FormControlLabel
                              key={Math.random()}
                              value={curCurrency}
                              control={<Radio />}
                              label={curCurrency}
                            />
                          )
                        )}
                      </RadioGroup>
                    </div>

                    <div>
                      <RadioGroup
                        value={transactionDirection || "IN"}
                        onChange={(
                          event: React.ChangeEvent<HTMLInputElement>
                        ) => {
                          setTransactionDirection(event.target.value);
                        }}
                      >
                        <FormControlLabel
                          value={"IN"}
                          control={<Radio />}
                          label={"IN"}
                        />
                        <FormControlLabel
                          value={"OUT"}
                          control={<Radio />}
                          label={"OUT"}
                        />
                      </RadioGroup>
                    </div>
                  </div>

                  <div className="ml-4 mb-4">
                    <TextField
                      required
                      id="outlined-required"
                      label="Description"
                      placeholder="Description"
                      onChange={(e) => {
                        setTransactionDescription(e.target.value);
                      }}
                    />
                  </div>

                  <div className="ml-4 mb-4">
                    <TextField
                      onKeyPress={(e) => {
                        if (e.code === "Minus") {
                          e.preventDefault();
                        }
                      }}
                      id="outlined-required"
                      label="Amount"
                      type="number"
                      defaultValue={0.0}
                      inputProps={{ min: 0 }}
                      onChange={(e) => {
                        setTransactionAmount(parseFloat(e.target.value));
                      }}
                    />
                  </div>

                  <div
                    className="bg-blue-600 p-4 text-white rounded-lg hover:bg-blue-800 cursor-pointer"
                    onClick={handleTransaction}
                  >
                    New Transaction
                  </div>

                  <div>{newAccountResponseMessage}</div>
                </div>

                <div className="w-1/2 border h-full p-4">
                  
                  <div className="h-1/6 w-full p-4 flex justify-evenly items-center">
                    
                    {curBalances?.map((balance: balance) => (
                      <div key={Math.random()}>
                        {balance.currency + " : " + balance.totalAmount}{" "}
                      </div>
                    ))}
                  </div>

                  <div className="w-full h-5/6 border overflow-auto">
                    {transactions.map((element: transaction) => (
                       element.amount === 0.0 || element.accountId !== transactionAccountId ? <></> :
                      <div
                        key={Math.random()}
                        className="text-sm pb-1 min-w-[400px]"
                      >
                        <Accordion sx={{ bgcolor: "#E0DEDE" }}>
                          <AccordionSummary expandIcon={<ArrowDownwardIcon />}>
                            <div className="flex justify-between items-center w-full">
                              <div>{element.accountId}</div>
                              <div className="flex items-center">
                                <div
                                  className={classNames("text-green-600", {
                                    "text-red-600": element.direction === "OUT",
                                  })}
                                >
                                  {element.amount}
                                </div>
                                <div className="ml-1">{element.currency}</div>
                              </div>
                            </div>
                          </AccordionSummary>
                          <AccordionDetails className="text-xs">
                            TRANSACTION ID : {element.transactionId + " |  "}
                            DESCRIPTION : {element.description}
                          </AccordionDetails>
                        </Accordion>
                      </div>
                    ))}
                  </div>
                </div>
              </div>
            )}
          </div>
        </Modal>
        <div className="w-2/3 h-full border overflow-y-auto">
          {transactions.map((element: transaction) => (
            element.amount === 0.0 ? <></> :
            <div key={Math.random()} className="text-sm pb-1 min-w-[400px]">
              <Accordion sx={{ bgcolor: "#E0DEDE" }}>
                <AccordionSummary expandIcon={<ArrowDownwardIcon />}>
                  <div className="flex justify-between items-center w-full">
                    <div>{element.accountId}</div>
                    <div className="flex items-center">
                      <div
                        className={classNames("text-green-600", {
                          "text-red-600": element.direction === "OUT",
                        })}
                      >
                        {element.amount}
                      </div>
                      <div className="ml-1">{element.currency}</div>
                    </div>
                  </div>
                </AccordionSummary>
                <AccordionDetails className="text-xs">
                  TRANSACTION ID : {element.transactionId + " |  "}
                  DESCRIPTION : {element.description}
                </AccordionDetails>
              </Accordion>
            </div>
          ))}
        </div>
        <div className="w-1/3 h-full flex flex-col">
          <div className="w-full h-1/4 p-6 min-w-[380px]">
            <div
              className="border w-full h-full flex justify-center items-center rounded-lg bg-green-600 text-white uppercase text-lg hover:bg-green-800 cursor-pointer"
              onClick={() => {
                setNewAccountModal(true);
              }}
            >
              create account
            </div>
            <div className="flex justify-center items-center mt-4 uppercase">
              accounts
            </div>
          </div>
          <div className="w-full h-3/4 p-6 min-w-[380px]">
            {accountIds.map((element: string) => (
              <div
                key={Math.random()}
                className="w-full flex justify-between items-center rounded-lg border px-2 cursor-pointer mb-2 hover:bg-gray-200"
                onClick={() => {
                  handleNewTransactionModalOpen(element);
                }}
              >
                <div>{element.substring(0, 7)}...</div>

                <div className="flex justify-center items-center text-sm p-1 ">
                  view account | new transaction
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </>
  );
}
