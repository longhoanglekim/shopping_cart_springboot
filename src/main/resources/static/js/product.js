
function getProductListByCategory(category) {
    fetch("api/product/categories/productList/" + category)
        .then(response => {
            if (!response.ok) {
                throw new Error("Wrong API");
            }
            return response.json();
        })
        .then(productList => {
            if (productList != null) {

                // Lấy productContainer bằng ID kết hợp với category
                const productContainer = document.getElementById("productContainer-" + category);

                // Kiểm tra nếu phần tử productContainer tồn tại
                if (productContainer) {
                    productContainer.innerHTML = '';
                    productList.forEach(product => {
                        const productElement = document.createElement("div");
                        productElement.classList.add("product-element");
                        productElement.innerHTML = `<h3>${product.name}</h3>
                                                    <p>${product.description}</p>
                                                    <p>${product.price} VND</p>`;
                        productContainer.appendChild(productElement);
                    });
                } else {
                    console.error("Cannot find the product container element with ID: " + "productContainer-" + category);
                }
            }
        })
        .catch(error => {
            console.error("There was a problem with the fetch operation:", error);
        });
}
