export interface Product {
    id?: string,
    name: string,
    price: number
    category: string,
    imageUrl: string,
    launchDate: string;
    _links?: {
        self: {
            href: string;
        };
    };
}

export interface EmbeddedProducts {
    productVOList: Product[];
}

export interface ProductResponse {
    _embedded: EmbeddedProducts;
    _links: {
        first: { href: string };
        self: { href: string };
        next: { href: string };
        last: { href: string };
    };
    page: {
        size: number;
        totalElements: number;
        totalPages: number;
        number: number;
    };
}

export interface ProductImage {
    // fileName: "java_logo_640.jpg",
    // fileDownloadUri: "http://localhost:8080/api/file/v2/downloadFile/java_logo_640.jpg",
    // fileType: "image/jpeg",
    // size: 27144
    fileName: string,
    fileDownloadUri: string,
    fileType: string,
    size: number
}