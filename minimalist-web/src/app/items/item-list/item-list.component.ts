import {Component, OnInit} from '@angular/core';
import {Item} from "../../shared/Item";
import {ItemListHttpService} from "../shared/http/item-list-http.service";
import {faPlus} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.scss']
})
export class ItemListComponent implements OnInit {

  items: Item[];
  sampleImageUrl: string = 'https://lh3.googleusercontent.com/proxy/4gmh4UQiDWuDED2ZVCTaDfIZfqHa62Vi0kr6XMc650bl1gYXZ65WUWKssJt43S5qDtbMeDl1xzGe3oRO10l4XA-bQsYrvLmposq8Hhs';

  faPlus = faPlus

  constructor(
    private httpService: ItemListHttpService
  ) {
  }

  ngOnInit() {
    this.getItems()
  }

  getItems(){
    this.httpService.fetchItems(1).subscribe(result => this.items = result.items)
  }

  hasImage(item: Item): string {
    if (item.itemImageUrl){
      return item.itemImageUrl
    }else {
      return this.sampleImageUrl
    }
  }
}
