export interface Items {
  items: Item[]
}

export interface Item {
  itemId: number
  minimalistId: number
  itemName: string
  itemComment: string
  itemQuantity: number
  itemStatus: string
  itemImageUrl?: string
}
