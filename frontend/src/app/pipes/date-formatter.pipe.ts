import { Pipe, PipeTransform } from '@angular/core';
import * as moment from 'moment';

@Pipe({
  name: 'dateFormat'
})
export class DateFormatter implements PipeTransform {

  public readonly DATE_FORMAT = 'DD.MM.YYYY HH:mm:ss';

  public transform(date: string, format: string = this.DATE_FORMAT): any {
    return moment(date).format(format);
  }
}
