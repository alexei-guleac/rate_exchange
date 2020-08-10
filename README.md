# rate_exchange

!! Before app start first create db schimb-valutar via pgadmin<br />
-- table Currency that represents currency codes which are filled automatically at startup with all avaliable codes in Java Moneta API<br />
-- table Exchange_Rates is filled automatically at startup with exchange rates avaliable on http://data.fixer.io/api/latest against the EURO<br />

## API documentation
http://server:port/v2/api-docs


20200809172250
## GET /rates

output:
```
[
  {
    "id": 1,
    "currency": {
      "id": 784,
      "code": "AED"
    },
    "factor": 1,
    "rate": 4.320354,
    "updatedAt": "2020-08-10T13:10:58.549+00:00"
  },
  {
    "id": 2,
    "currency": {
      "id": 971,
      "code": "AFN"
    },
    "factor": 1,
    "rate": 91.55869,
    "updatedAt": "2020-08-10T13:10:58.549+00:00"
  },

  ... etc.
]
```


## GET /rates/get?code=USD

output:
```
{
    "currencyCode": "USD",
    "factor": 1,
    "rate": 1.176118,
    "dateRate": "2020-08-10T13:56:52.650+00:00"
}
```


## POST /rates/update

body:
```
{
    "currencyCode": "USD",
    "factor": 10,
    "rate": 16.35,
    "updatedAt": "2020-08-10T13:02:48.002+00:00"
}
```

output:
```
{
    "id": 142,
    "currency": {
        "id": 840,
        "code": "USD"
    },
    "factor": 10,
    "rate": 16.35,
    "updatedAt": "2020-08-10T13:13:14.345+00:00"
}
```


## POST /exchange

body:
```
{
    "currencyCode": "GBP",
    "rate": 18.86,
    "amountReceived": 100,
    "issuedAmount": 1886
}
```

output:
```
{
    "id": 1,
    "currency": {
        "id": 826,
        "code": "GBP"
    },
    "rate": 18.86,
    "amountReceived": 100.0,
    "issuedAmount": 1886.0,
    "performedAt": "2020-08-10T13:11:44.510+00:00"
}
```
