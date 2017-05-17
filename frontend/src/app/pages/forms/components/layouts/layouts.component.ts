import {Component} from '@angular/core';
import { NgUploaderOptions } from 'ngx-uploader';

@Component({
  selector: 'layouts',
  templateUrl: './layouts.html',
})
export class Layouts {

  public defaultPicture = 'assets/img/theme/no-photo.png';
  public profile:any = {
    picture: 'https://lh3.googleusercontent.com/-z_36zrxoDk8/AAAAAAAAAAI/AAAAAAAAAEU/RgBNBQBTXK0/s96-c/photo.jpg'
  };
  public uploaderOptions:NgUploaderOptions = {
    // url: 'http://website.com/upload'
    url: '',
  };

  public fileUploaderOptions:NgUploaderOptions = {
    // url: 'http://website.com/upload'
    url: '',
  };

  constructor() {
  }

  ngOnInit() {
  }
}
