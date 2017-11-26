import { BaseEntity } from './../../shared';

export class DataFile implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public location?: string,
    ) {
    }
}
