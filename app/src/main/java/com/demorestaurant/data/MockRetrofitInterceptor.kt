package com.demorestaurant.data

import com.demorestaurant.BuildConfig
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull

class MockRetrofitInterceptor : Interceptor {

    companion object{
        const val JSON_LIST_RESTAURANT="[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Noma\",\n" +
                "    \"image\":\"https://www.linguahouse.com/linguafiles/md5/d01dfa8621f83289155a3be0970fb0cb\",\n" +
                "    \"operation_state\":\"CLOSED\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Asador Etxebarri\",\n" +
                "    \"image\":\"https://stargemsvietnam.vn/wp-content/uploads/2017/09/Restaurant4.jpg\",\n" +
                "    \"operation_state\":\"CLOSED\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 3,\n" +
                "    \"name\": \"Geranium\",\n" +
                "    \"image\":\"data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTExMWFRUXFxcVGBgXGBUYFxUYGBgXFxUXFRYYHSggGBolGxcVITEhJSkrLi4uFx8zODMtNygtLisBCgoKDg0OGxAQGyslICYtKy0tLSswLS8rLS0vLS0tLS0rLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIALcBEwMBIgACEQEDEQH/xAAbAAACAgMBAAAAAAAAAAAAAAAFBgMEAAIHAf/EAEIQAAEDAgQDBAcGBAUDBQAAAAEAAgMEEQUSITEGQVETImFxBzKBkaGxwRRCUpLR4SMzYvAVFkNTcoKi0iREY5Px/8QAGgEAAgMBAQAAAAAAAAAAAAAAAwQBAgUABv/EACsRAAICAQQBAwUAAQUAAAAAAAECAAMRBBIhMRMFQVEUIjJhcUIjUoGhwf/aAAwDAQACEQMRAD8A6DwTXZqSF51GRt1frqZ0jxJG8NIS76M4O0oWgv2c5thyTdHQBg0JKz3qtwFxxIK8nMjzPfC9r7B1iPNcm4APZ4g+M/jI+P7LsrmC2o5LiUD+yxd1r2z3Te1tmDLJgZE7dPHcb2uhFRBaN+t1JW4iwhoDtbDRV3VQDdeaXu04LhviLmwbonSMs93mp8TF2DwIWV7f4hUlW28RQk4uIm1nKqZewdtmhX5PXah2Dv7oRGoPeamq8cgQFncLOgjsNVDWxdzunkbqm+pOYNAub6K9/h8p3c0e0oqAKOBMl2ySAJx3hDTEng9ZB8bp9b/MKSMLhMeMPYd879vEBPL9JVl6lDvzNbP2j+RnDSIgbrnPpQjc/sg3U3J+B1TzjE7mwNy6koFLRtdke+LO4De58yLJxkG4TNNrq+U7HzKXAlSwUjGuGoc4W9pTs2UOj9iRsMoXU7nF3ql5e0ctTfKik+LO71mgNPuCBbWFrITswL3E2kGXTAc1ri1iuVSC1a4D/dPzXS3T5mMcNbg6hcvfIRUOfv8AxCbf9SW0egavdnsiavp+kt1DMqDoTprsxLRqAjba65DBe4CFUlQ5waclrgHyWjszpwWkgDQ2VNF6fbRuye5mWEq+347kPG7rU7h/e6reixusvsWnFzX/AGZ5zXGa1jvvoqfA2eMvBaQTa3XZaVFL1KQ3zGUsApJ/c6L9rb2hBtoCvIsQjc03OxSphxc+Z2pWZTncGn723gjkgL3zEVvdvxEEcXytdVstsLfNPmFy/wAIAbWSLimCTuna/KLHbXdNmHlzP4bhbbVXZftWHZjxLb22dvbRaYiwnKB/+q99iY7723NVJzHe3aDQdQkr6i6EZ4kitjwJtTttC66rU9QAAAFPIWuZkEg1U9BQBgsSD4pulAFX9Sj1uGxA1VF2lVEy1rXcmh8DTu0IYcv2kOzjRh6cz+isV2KRxi9wSdAAQrsq5zD11EDqXhZYq0NawgG41XqphYTaZz30eYiymhc3Vwccw800P4sjG7SPj8lzqgqrBuW2UjoisUoKxHv1dZwTxNf6ahxkCOVLxTHKbR69dwR70h8URMhqGVGXv57EdQVfMjmglhs4jp06qZ9PBWNY+b126HW2vWycp1TNWcnmJtpNlnPUD4lxvT52gRODgNTyKlZx1GbM7Nw1GumiW/SNh0ET4nQi17g6+aW4ZO8PYututIGTCJpKTnidWr6gF7ba3CtSaxO8kJww5i0nXT6KbEZXNkaAe705IVb7nL/EOyAAAS/hD9FbxLFoYy1r3tB31K8w1o10XNvSVERUBx2IsEzSxILD3gHGWxH2o4qYyVpjyuA8d1dZx53bljbri9BPoiLao9UqdTcjdwg01TL1HuqxmmY99Q5obK9u9h63PZFY8Ujkp+1js5w181yfFHZo0f8ARnV5myROuQSAPamqHN35QF9YTqdDlxpro42lwu/lvryB8FDW1r2uDQ0Cx1A215KJ2Cxh7Tld3TcWPzVyopM5zWOtjv0THgbJ/wCoo7Jxgf2QVOJgsyvAyh416eC9raoXjDQC0usQBuPFTw4W1xDXtNnEE681We2aOVwyBwbmykkaW2ug3stLKXlK6wTxBvGuIvpGuEYAEmw3yAjkkLDA3OHF/MG3MuXTa8CVwEjQSRsdbFLEvBwfM8NlDcmUgW66q1vlxvrPBmpotdZRuRfeeYdxPUfaBFYEFwaP78kfhxN5qHxgjnr4qHDuGwyVkjpA7Kb+4K9DhTBL2mexzE+9RWueSe4k+1nJ2wJU8UCU9kY7ua6zujiCjmGY2MzAGC4Iuedvdque1MuWulaHado7bpdMeFRPNQWN1sW68u8LhHIO6SEXbzC3EWIujlkjaA0B17jex1QCu4rbARmaXkjkrXHz7VWn3mtP0PxXO8dkzS26WCzfCq3HH9jdaqKsgdx24k4qn7KBzGuizC4vbULXDuIJXWzvcShXGNTmkp4+UcTR7wp8NjAyoesvO4QmnoUrkiEzxBUfaeza85Muo80Pqa55f6x36q5gNSwOqXujzkGwPgOSsRyEZZGwNsdbHdV4lQQGOBD+BUzjGS8EaXaSd0Rqqp7KN8jyWlrTYb+WyXBx8O1bT9j3z46Be8eYmRTFu2cgFP1stSBezFrU8z5izDjj+x9Ylx3JOy2psSkljDcxvmA38UFhPcUmD1PZknexvbyQqLNznMZevavE7TSwAMaLE6Dn4LEq02LVT2NcAACNui9Tm+iZm14kcJ1uZvZndu3kmamlsfNc4warySNeNjYHyK6NPFZgc3lqlmq8iH9TUrfaYbjizN0S5j1EB3tQDvYka+xMfDk4ccq3x/D7XFtChLR9m4Sz2fdtM4vjjcstg5xbuLkm3vW7DsVLxc3LPbwWUtM4sBAXWDCjMrQwyZ0DA5O6w+AV3Gn95hQPh95DGg8kVx4nK0g6+KBpz9zCGfsGHsNmSd6Torxh3Rw+KsUMNYSCJo2jplJ+qp8X0lSYn9pMxzQA6wbYn23Tem/DEWfsxGo36FXu1QylOvmr19ECxfuh6j9snnkzRuHtRT0YVIZUPHUNt53A+qEx0r3NNm6Ec9ArvD2HSQTslu3wF97aj5Ka7RWDzB3LunR67irsXljwSQSO7blzur0XEA0FjtfkucYrUzzZ5iG3abECwvyuOqYWvALC6+Xs9bb+rqgWeoXrjBHMqNNURyI3U2M5nlrWai3PxNvkVPi0QkJkufum19L2SJW8Q2hfFDYvc4WcbXDLb6e1bUOIukdHE57m3tsbF2g5oj2tchW3n3EE9G07q4x1rhG5hJJJ+aUcWmqJKqQQvLCbW8bNCj42oRG1ro5pS8uAF37fBU8Aw+VlSySQuPW7hz2TdNyGpUMotTrljPaDiOpppHmozva1h7psDcmwsVRqONaYyZjFJe+ve2+KduL6Ns1LI0NBebFruYtuFxWWhLnWHjryKdesKdsCrtt8kdoOJsOfJmdDIHOdq4k6XO/rI/JxvTwRvDQ51pIsuouQBcankuUyUDmjQfEKOQGQjKDfTS/sVlO0QJdmM6Vi+OirkbKBYZbW9qWZBnqAOrwiNNTiMBg1IGvnuQqmE0Zkq42uabOdzvyWaGzazTUKEVACNfFWGsNS0tcPUaDr0C2paUi5uDYX3Co0PD7qionyuAax5aLklFajhJ0cb3Zho0nS/L2pfUeOx4SlmRcS9wAxmVxduXm+2uqJYjBmcQwaa2sl70b09nFzu83KTbxR/HcXFL2brXzg2CZs058QIiZtG+IdPCf8UAO4P0CL+kiTWNvjdB8Hq3S4mZXC2Y6ewAfRMuP0TZ66ONx0DCTqhP2P5CJ8xPYO6pMCpzJOG8tymPiPA44YwYzre26oYBH2bHP+87uj6pUsawT8xpjvGBGKbFCCQ21hoPYsQYvWJLaf90J9Ms5tQycl1zgyYVFKNbub3HeY2XGxo8+ZT56NsT7OcxnQSDbxC9RQQHx7GZLE7Mj2jdhD+zly7WKdMQi7SK/OyTMfZ2coeOe6asCrBJHbwU1rtsaswlpLItgnJfSThvqTAc8pQCgxAhgBfay6ZxhQ5o5Y/NwXHxROLgAbXOX2qmorVwJWuzaSY/cP1QdE9wdctKY4gJmgO89EncG0rmMnY46hMNHiTIY2ud5ab3WUy7HG2adKtcAFHMuQ1Bjl7MbDQX8kfmwsTROzgDO0tB5a80lPxRjpRIbt2v8AJOFPUnsc7CHA7a/MJytQrjjiV1mkupTLjEA0vo6gYQXyucelwEUj4Mpm7Rhx6OcfghdVX1Tj/KDgPBeS4/PYZ49W8jcFNWWVBMkY/sxzayHky3XYYxs8LZWsbG1p0Dhd1/BXX00X2mJjABH97awB0HxIQB+NufbNTZrai7SdPAq3Hi0jhbsLf9OvvSzaQPXhSIzVrcElh7SXFcBs6WJlnt9Zp0GU31HirZoHFh7h9Ut1tyFivZYDOzNqyQa3621VN2KvyPBDiQNwNNV5a8WhtvwZo1lSmVgrBuHCyVzp7MbluNddAdvgjVPhjZgJmC5znKdjoLXK8khL8vNxAaPaNSR0sCmQQiKNsbNHAaezcrX9LrN7mx+uotrH8YwvcXMS4Se8tzAO1vfMdD4r2g4Ydd/aOykEZC03b7b6pgpJ5A7vO0+auyObYuuL2vy156rYvoWv8ccROq134MBTROijk7YNy92zr6HW1h0Juua4/wAOyU0jiGkwvJcxw1DSfuutt5poqMUNT2sEjrtzAhuwAHQhEcNxYNi7Mi7RdoLiCT4aqv12fyEdu9M8S4zz8Tlj2C2p38/0R7gfg973iaVpEYNwDoX9LDoumS0FOGte0N9VjtmkXO+izODbvW+Gy7VXfbhYrpqADkxWxfAqh8hMUcbR1J313RGk4Pkc9sjnxgttbLfTTVEp6zXQ6e35rKHFA14F99CN0hXYemmjY5K4ErUvCroe0Mc7szyXG4Fr+xa1XDtU+JzTOLkdNEw1lW0OBFvH+wtW4ixjbuN0Q10lsxffYF6i1wNgNTCJbvY0hthzv/d15xhh0g1kmZ3YHEAjW6P0ErXG7BcOKi44a0ie4BywW18VqaWwOkz9Qux5x3h+0gke6bs3Rtu3QnMfYto5KgyZxnc4C17HnqmfgCF/2Wd7GsIzAWI1NrE29hXQOFm5jM90YabgAWH3WqzadGAMql7KxnEqnEpXkMJNwRpr8k307LMA6C3t5rXiunacQJAADWg6C3Iqw3RiyNYiqdomppiWG8yq5uu6xRvn1WLMMfyZzWVjs5NjuiUMzo5GyC4IIcExNMEepOcoRjdcJCCGhoHTn5rcSxmI4mSalXPM6biNY2elbICNgfL+yq/DWNCI95wA8UhYNi8jW9nq5huLcxpyVo0bpCGkHKdrbkE9Ew4Ntqle/eDRhXUwadAx6va4h4sQeY6Fc1q8ImLnzRsJjikGa3K+u3PRPNNgbuxDAQGt5uOoHkpqKX7GC0PY8SOu7MCNwBlt42QdTa1YPGeZdU3KCIE4brWSSTAW1APw1VGrm+7fRt000+B0DZnzAOic4atzZW6jWwRCOno4Wl0cTHOHM946/wBSx7dfWhyoJm16deukUvYMkRCgGc2BF7Hc9NbeaJ8MVsju5mcwB+1+XPlzKaJq4EgtpoTpvbvDy6KLCcMpe0dYva4kXBIsOel/FNU+op/kOfaU1/qVmtAVOF94QlpXukJbUOazk0W006lVn8PukeC6ckDQ6DRWatlOO52pa7W1nEH43C1hr4YI3HtCQLlznEX9yzNTda9m/dkfEzjpqyuMZMyjweSJwzSBzQO7bTS/MIk+QCyRKz0ifxWsFMSwC4LnOa4gnQ26LfEONHR2IpW67Elzgbo49K1Nx3b9oPtFxbVWNoGY+UscZJcCSSCLZnEa87citHxAR2yDa+tvK659BxtNILaR8iGi3uKeMPru2p2ONwQMoJ+8GnqtQelVmsLYdxHvBjUsDwMCW8IprPc54abXbcC1wd9fBQYXRZXyFxJsS1tydkAreMGxlzBe4NrWJ156qDEOJDExj8xcXnVoB96f06VUIqrB2F7CTHkPaCCdjo7yO65fj/E7qeeWDtHWY4jYWLTqOXQhEhxhmsAHC/gqdfgzZ5HSOddxtf3WHwAU6ooVltMrKYrx8SNDgdfcP0R2GtvZ7dWvHuPNCZ8LDXFo96nOWNti7T5LOsqBHAmmGfOWMcPthEDc2/dt5G+qHz1+tgbCyjxWsb2MDgRbLGPDVpP1QT7YC7dCv0+SP5K12DBhqOvN7ZjbpdTUldrvz9yXPtYudQspqgm4bcknQC2qXGmhPII7f4xuR5eahZVucC47cgevRL0UhY2z9DbxKuTVhDGmMEhupuL68iifTsoziQ2oVRHLCa5sGXML31PggXH3EbB2zWteXSRhrSBcX1ul2qxaod3gHWve4GgGmm226uGsfkMkxAabubpfQ8vNN0aoINoExdRcbDuCnjuQcLYz9lpHxPikzOeHghuhBtz9iZ8J4xZFG8uilOdxOjCdOV7JUoZJ6hobazL2sd8ovb5pw4fwqcU7i5wA10ABsE1RqjY2D0IGsOwLYilU1/2iV8waQHusAdCAOqtuNmqk8/xLeJW+IzWasa9y9hM9FQu1AJSfNqViplyxU8JhfKJSh4cllILZQY+brEe4dUzN4NeyMEQl/i8gX8QOiLYfXMEzBILBjiXNOhBF7ac9dUzYjiQcLx6gjnor6nWPXwYpTSGI2wFgfDLY29pMxgedr2OXyGxRV2H0zyCS1rhbvNaBaxvrySfxLjMhtGXltt2jS3TvboW3GZhGY+0JYeX7qqLcrhw82a/SfLSSMZ+J0aKggaSXVD335aAeQsEKrqeje69pCbg3LnHVv9I0SfQY3JCQ4d4Ag+zojMnFVi4sHrEEA8uq7UtqM4HP7EUq0F7ttAwRDtPQsc8vnAfpZoty8fFSTQwiMsBbEwanLcG/iShJxAVbMrXdjKNiNioMLw/snntpe2lc0hrBewJ5k7JDxvjDt/xiMXabH2uplqhxSFt2w3NyR2jgXNLhbQOCq1UbhM6WRzmRtDbAW/iE7jrZWqLCHiERlzYhdxJcRfUkm3TdXB9hhbaWUSkagE5vcBojV1ksQoJmbqFrRMK2P1KEEjZxJHlt+Fw3965rMXh72mUmzyCSdDY7npqum4h6QKeNuWNgt0u0fALmVdXQuPqC1y7373cdStPRad1JBXA/cyVAQlt2YboMZMsToXRNkkaGsbJp3QOh6p5wbCI5I7VDQb2yi9i23O6SeFIp5nDJHlivsBYHxJXWcNwrVrnaNA25e1bNOnCHMo9+4YizV8GtYbxNzDxtceSKzYc50McdywNLjYA371r6+YTK2Rl7A3sfl5KrNh+ckk2vfr1urMADlZUNn8ooVHDvfNm5r7G4W7+Hnu07PbQatTQ3DHad7QK6yGwsqjJ7EIbAOokf5Zf/ALfxat34NUXJEY1t95vIWTqY15kUuA4wZUWkTnNRwxVEk9n/ANzUOqeEal2jmtA1++w/VdVfBcKqKZ4doBYixINj8dEPwiF+pOIl4pwyJaYRRm72iPQ90d1tilR/BNQOQPlI39V12OF9/VPtcFrHhvOzQOY0Klq8yotwJx53B84+6fzj9VcwTh+WKQvyHugnUrrv2Fn4G+4KGaJjQSGtHK4A5qj1YUkS9d+WAIiBDhrKhp7Sdrf6W2L/AG32W9fE1pGQOaMoYLNHePLVHaZlPTuJlZFE95sDc6i/d319yhr3EtzMaJNbdw31B212Gm/gsu3yP0eJ6Ck1IMkcwE4uijfGb3O4cdxzDTsCqcULnxgXIaNWg2T4cIhMYc+NpeRd2brzvyQOpkZI4wjK02s0AaN8T0SmduB3E9TprdUrFWA/9lXhilldJ/DFw0d8kgfPdHqp0rA6Njgxx1vuLc7tHUILh9DJTvLg/NffKTbxTLC6KRwD2kEjLY3F+fNGpfHCnmE02nsSgC4Zi5/lt477HNk0uWi+a3UX38kvYq83DSLarrdRWwwMGdwY3YXsNEKx7BaaoAzkMe4dyQbG/K/NPtoQw3CJnVBSRjicaml7xWJrqPRxV5jlyObfQ3tceSxX+maL/UrGDizh573Z7F3lv7zquY12KVMDi0iRoHi63xXUf8dqGaB97fiAN1Vqsfe4fxIYn+YRjZRZyRAHyp0ZyybE3SjtHO125clpHi9uafpa+l+/h8R62Q2qgw6TeEx+AcR8bKvhqbrqaFfqt1SjB5i/TYkHbOa3rmuFrV4jlGj2uHRpvZG48Ew87D3yFTMwSg/A32yFWGkSXPr2pIxmLtHjbh77gk2ARfCayTM8vcQHjZp18wi8OH0It/Di021JRqjngb6rWexo+ZXfSqepev1sgf6v3Y9pz7EKWsfK5sPbyN0sbOsbgEi+2huPYp6bgfEZd4y3/m4BdTjriR62nS4A9wU7XX5/34ElMhEUTAtuNjkgYzECh9Fj956hjPBuvxTNhvBWHwWJDpXdXI2GrZkZ5AlQbMdCU5Pcliq2sFo42tt11WktTI/dx8hoPcp4qBzlfp8OA3Qzvbsy6gDqVsPpzdFGqtiFQIo3P6NJA66aBAoeLAcodGbuLRpsQ7mPJUa6uvhjIZ+cGM5XlloZwLX+YVV2LRZ8l9bX0sQPM8lfyp8y5GO5dssAWvbtXrZm9VbevzJ2N8TLLxSCx5rC1WyD1IxjuR2XlluQvLKZE0yqCpgDm2VqyqSVrQ8scMvQnYhdOEXquVgGSZgfl1bdoN+lgdiEl4o+VrnOgcWRk3yjYHfZdKxTC2zN+oSbXYPUREkNErOmxWY9DIxOMiaSXB1wTFuTH6nL/MDrcjzQum4ikhe97WNzuFiddB4dFLirXMeXCNwHMEbe5CZqlruVj4gotddY/wAZQ3WLwrRooeOADcwuzdQ/c9SCjNJxLFIQ57pA9ty31clyObVzXOAbhXYZW8yb+S46asdCXXX3E/cY4cUEVL2vbVEWFiC02HkFYw/EwKYU8kjpLOzB2zh4JZoxmOVjXOJ8LfNO+F8IONjI5rR0bqfejorYxF3sXdu95THEZb3QZLDQd5YnCHAIAAOxBtzJNz5rEbBg/J+5BNw3I7m/3s/VCqjg9/IE+cn7KF/GNWPwfl/dQycdVXRn5T+qCHT2Ev4nEik4RmH+ncf8z/4qpLw1IN4x7XH/AMVYk46q+jPyn9UOqeNKs/g/L+6qxB6nLWR3I5cDNrdmL6/f933VVOAv/wBtv5v2Xj+K6rpH+T91jOLKrpH+T91TLyxRZNFgcvJjfeT9ERpOHqgnRrPaT+iHxcW1XSL8n7ohBxjVdI/yfurKze5lTVxxDtJw1ON+zHtcikWAS/0e96XYeMqn/wCP8p/VEsO4qlc9okLAw7kNR/MAIL6YwoMDk/Ez2F6LYXSujHfObp4e1T00rXC7SCOqkadQEHzkmW8CibOntsFUlqyt8VqGM7re8fDkhBlOmu7hty6rM1fqArbYDkx/T6IsNxHEr8SOe6EkEgA97yt8fIIBSROkiAcG6axvBIcbbZmkaJjxXEo2MLHlpJbs4HX27pHZCxwP8MZXXvv/AH7UDf5OG7mX6gFqsDKYy0eIOezI/Qj1tQdRzFtenRT0kIbIcxHeN7Wvo0c+mqVIKZ7QQx5DG3dcnb+m51CY8Ep3m8j35nOAA39UaobWFFIjWnq+qtFo6hx7CdiSSsJcBqq4imIHfFr/AIeXhqrogdluQld9u7Im6EUDBAkUVSeR1VyLECN9UPqS1rC+405DcqpS17XnK0jN056LQq1ZHvFH0e7OBGVtcDyWwqAhER1A57ojGwp+rVM3URt04Q4MtKrVwMeLObcfH2HkhuNY0IsrYyC+5zX1sOXtuhNRxW4N0LXO6ZdPfdOC4RcUMeoTfSzx/wApxc3odx+qi/xtzdJY/aQR8bITHxVUO/04/wAqqVfGc407KP8AKp8wkihpBifF0ZeWfZM3jmvfysFFFhzqixMLKZp5uu55/wCLPqVjuNZv9qL3H9VLDx1MPuMaOdgu8yTjQ8I0/ClKAO7nPVzHm/sAAVhvDkH4W/8A1uH0Q48by7jJ4Xb+61PHU3RnuKk3LK/TvDsHD0A1BLT/AEtePoiEdK5nqy38HNcPjlSp/nyYfdZ7itjx5P8AhZ7lIvUTvpnMcm1jwNQ3/u/RYlIcdz/gb7v3WKfOJ30zwW+O6FzRkHUIiyZeyEHcLO3YmmVzBGQHkqlVGG7hMTKYH9roFjzQHAKQ+ZBrEoZmrYOaqpC9AViZAWWe0aFsKgKnlWzWLsztsJQ1fgpzVu5BVKdtgrBepDyWTEYOGKudryWuswakHUE8gE+0eItmjOX1ubTuEucP0AdSNc0i5JO25BSdxDjs9PUAMNsh71uZPI+K6xTj+yldZsJ2+06FA09o/NcW1udAq03EELLhhzu2087b9UNoOJo6qICePMD95pt8uasYRw7StfnjmJH3WSAXaTvrzWO2j25YcmPLeuQH4lCmwiSeQuIs0m/8QuuR4InUcNZfVcBoL35n6BH2UjhqNR4G91C/DL6vzm/K5AHu3Qqzb+JUgxa7RaS07uIsf5YJNpJBY37oJufG9/7uh+O1b6fu5HBrW5A8EkEEWBPinmOgYNS1ottob6eIKr4jTxyNLXNBBBvvYW2Fj1ujlCo++M0eOsYrGIk4GamQXZI9rdwHOv7RqjjmVQ7rpbE316jT9UQwfBBAzLGzNYk2vf2Xsr0lHI77gb4kgWSNhuZztXiNi5MfcRAVJSSv8b87o3hOENj75N3nS9tvBWqOJkYGdwLv6Vu+u5MaB5p3TaXaA7xO/V87UnrIde0f3WgaDmeqH4zUyPY7szlsLgfiA38jZbPqc7t83j+inihtdx2sSfdcnwWjSh6UcRCx8nLRFYb6m5KnjhaRf6BU45dfC5t5X2VtkuiKRiWHM920CgkpQVLmWXUzhB0mGdFoMIKMNVuFl1BlhFp2F+awYWeiZ5ofNRBigSDAIwgnl8VepsC6oo1qstergSpMrMwxltlitZvJYpkRWBU8cniqgcVJElcxnEK00FzyPNBuKKIDvDe9uaZ+Hh3776fWyp+kKItLDyJNhsfmhqx3S2OJz7syrUNHcXWP8lbozoR4IxacEg+WCxXscRKsSQ7lT00YUbpYLI4oyNF7JGVON1s6ykGDYZjDwZjrYv8A08jrBxuwnbMfurOMOGXyOMkTWm/rAG1/EX5pHx1lwLXVzh70gSwARzXkYNLn1gE7W6um14stz6azekq0zp6OTWN+Q7gg/BOmFYtDM27XC/4SQCEQw7iKkqWizwCeTrfVe1fCsEveblB6sNj7wofSZGUM6zWI5yy4ksEjh6ryParcdfMP9Qnz1QB/D1VH/LlJHR4v8QtAK5mhja7yJH0STU3L7SosQ+8ahis3Ue4LcYrMeY9wSsyvqhvTn8wUzMQqj/7c+1wQyt3xJynzGL7ZKd3Fean1nfFBGGtdtGxvmSforMWC1L/5kxA6MGX4nVStNze04ug7Muz1kcfrEX6c/YFX7WSbQAtZ15ny6K1SYBFF3ja/VxufeVriPElJTDvSNJ6N1Kar0R7cwLagdLCVBRBoS3xZxMwE0kDruP8AMcNgPw36pR4h4/mnuyEdkzr94+CE4HATK0nxJ/dMsy1rtWUrVnOWh+EEK1mUWVbgpUmOhZsHLdpWgWwJXZnYk0Z8URw+PMRrZDWORLDJbOF+qqx4lgJcr4S3mCh90WxuRuljy30QYOXI3EhhN8ykD1BmW90XMGZLmWKHMsXToCBUjD9Vqxq9vbmkjGxD3DrwH7X67D57qfi7+K2xIsBcf3awQWOUtIcNPK9vOy9qqsv1J+HxJQ9p3ZlsiLjYNTpzVqKIBTPaLrwBFnE5kL4xsvWwgLclYXLpEiLRdeZfBTrwhSDIMGVjbmyDVuG32TLUQX2UD4dOV0RXIg2QN3Emalcw6aHqND71coeI6uH1ZnWHJ3eHx1+KPy04O9lTnwth6I63Yir6f4hCi9JlWz1g13tIRen9Kx+9D7sp+aTH4J0KruwdyMNR+4E6dvidJZ6U4ucJ9zVsfSnFyhPuauZ/4Q/wXowmTwU/UfuV8J+J0WX0qfhi99h8kMq/SbUu9UNb8Upx4M/mVaZgw5lVOo/csunY+02xDiaqm9aZ1ujTYfBUYaZzze2vU7oxBhrRZXYqfkBbxshNfmHXTY5MHUtBZHKVnZtJ+8fgFJHThvitzGTqdkFnjCoBwJPA67QVK1QxMsNFKFGZOJs4rZrlosYuzOxJ2lWqWQX/AL+qpNKsN62/vqoPM6Xqqe+30HyCrCQrDJfl8votSOqleBiQTPcy9L1pZelEEoZv2nn8Fi1zL1TKwI2RStA1WLEoeowJajjsL7rSXZYsVAeZeVXbLQFeLFeQZ7dZYLFimcJlwvcqxYuM6eG3wWr4r/2FixdIlOSMWuoTHfZerFcTpnYrR0KxYpkTzs9F61n01XqxTiVkjITdStpdL8l6sUYk5kzI26aKYC+gWLFQmWEkaxYLLFikdTjPWnZelwWLFaUmzHL3MsWKZGZ6JdVPG/xWLFJE6WIjzWxZ1WLFX3kyArUPXqxEEEZtnPVeLFitKz//2Q==\",\n" +
                "    \"operation_state\":\"OPEN\"\n" +
                "  }\n" +
                "]"

        const val JSON_DETAIL_RESTAURANT_1="[\n" +
                "  {\n" +
                "    \"day\": \"MON\",\n" +
                "    \"time_work\": \"11:30 am - 9:00 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"TUE\",\n" +
                "    \"time_work\": \"11:30 am - 9:00 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"WED\",\n" +
                "    \"time_work\": \"11:30 am - 9:00 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"THU\",\n" +
                "    \"time_work\": \"11:30 am - 9:00 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"FRI\",\n" +
                "    \"time_work\": \"11:30 am - 9:30 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"SAT\",\n" +
                "    \"time_work\": \"11:30 am - 9:30 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"SUN\",\n" +
                "    \"time_work\": \"11:30 am - 9:30 pm\"\n" +
                "  }\n" +
                "]"
        const val JSON_DETAIL_RESTAURANT_2="[\n" +
                "  {\n" +
                "    \"day\": \"MON\",\n" +
                "    \"time_work\": \"11:30 am - 9:00 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"TUE\",\n" +
                "    \"time_work\": \"11:30 am - 9:00 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"WED\",\n" +
                "    \"time_work\": null\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"THU\",\n" +
                "    \"time_work\": \"11:30 am - 9:00 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"FRI\",\n" +
                "    \"time_work\": \"11:30 am - 9:30 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"SAT\",\n" +
                "    \"time_work\": \"11:30 am - 9:30 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"SUN\",\n" +
                "    \"time_work\": \"11:30 am - 9:30 pm\"\n" +
                "  }\n" +
                "]"
        const val JSON_DETAIL_RESTAURANT_3="[\n" +
                "  {\n" +
                "    \"day\": \"MON\",\n" +
                "    \"time_work\": \"11:30 am - 9:00 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"TUE\",\n" +
                "    \"time_work\": \"11:30 am - 9:00 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"WED\",\n" +
                "    \"time_work\": \"11:30 am - 9:00 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"THU\",\n" +
                "    \"time_work\": \"11:30 am - 9:00 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"FRI\",\n" +
                "    \"time_work\": \"11:30 am - 9:30 pm\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"SAT\",\n" +
                "    \"time_work\": null\n" +
                "  },\n" +
                "  {\n" +
                "    \"day\": \"SUN\",\n" +
                "    \"time_work\": null\n" +
                "  }\n" +
                "]"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url.toUri().toString()
            val responseString = when {
                uri.endsWith("restaurant") -> JSON_LIST_RESTAURANT
                uri.endsWith("restaurant_id=1") -> JSON_DETAIL_RESTAURANT_1
                uri.endsWith("restaurant_id=2") -> JSON_DETAIL_RESTAURANT_2
                uri.endsWith("restaurant_id=3") -> JSON_DETAIL_RESTAURANT_3
                else -> {
                    ""
                }
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    ResponseBody.create("application/json".toMediaTypeOrNull(), responseString.toByteArray()))
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError("MockInterceptor is only meant for Testing Purposes and " +
                    "bound to be used only with DEBUG mode")
        }
    }
}
