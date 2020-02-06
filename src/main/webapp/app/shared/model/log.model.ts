import { Moment } from 'moment';

export interface ILog {
    id?: number;
    date?: Moment;
    error?: string;
    evento?: string;
    dipendenteId?: number;
}

export class Log implements ILog {
    constructor(public id?: number, public date?: Moment, public error?: string, public evento?: string, public dipendenteId?: number) {}
}
